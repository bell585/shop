package com.example.demo.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    public static final Long JWT_TTL = 5 * 60 * 1000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "agshynf";
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");//return token
    }
    public static String createJWT(Long userid, String email, Object Authorities) {
        System.out.println("类型是："+Authorities.getClass());
        Claims claims = Jwts.claims().setSubject("user");
        claims.put("userid",userid);
        claims.put("email", email);
        claims.put("Authorities",Authorities);
        JwtBuilder builder = getJwtBuilder(claims, JWT_TTL, getUUID());// 设置过期时间
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(Claims claims, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setClaims(claims)   // 主题  可以是JSON数据
                .setIssuer("sg")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }


    /**
     * 生成加密后的秘钥 secretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");//return key
    }

    /**
     * jwt解密
     */
    public static Claims parseJWT(String jwt) {

        SecretKey secretKey = generalKey();
        try {

            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();

        }


    }

    public static long getExpiredTime(String jwt) {
        Date expirationDate = parseJWT(jwt).getExpiration();
        Date currentDate = new Date();
        return expirationDate.getTime() - currentDate.getTime();
    }


    public static boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }
    public static Claims getClaims() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        String token  = authorizationHeader.replace("Bearer ","");
        return JwtUtil.parseJWT(token);
    }

}
