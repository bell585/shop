package com.example.demo.util;

import com.example.demo.execption.MyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    public String getToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        String token = Optional.ofNullable(authorizationHeader)
                .map(header -> header.replace("Bearer ", ""))
                .orElseThrow(() -> {
                    throw new MyException("token为空,请重新登录");
                });
        if (!JwtUtil.hasText(token)) {
            throw new MyException("token值为空,请重新登录");
        }
        //判断是否为黑名单内token（登出、注销请求携带的token）
        if (redisCache.isTokenBlacklisted("Authorization blacklist", authorizationHeader)) {
            throw new MyException("token无效,请重新登录");
        }
        return token;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String requestPath = request.getRequestURI();
        String[] list = {"/Shopping/test","/Shopping/static","/Shopping/user/login"}; //允许跳过JWT过滤器和鉴权认证的请求路径，及非登录用户可以请求的路径
        boolean isContent = false;
        for (String a : list) {
            if (requestPath.startsWith(a)) {
                isContent = true;
                break;
            }
        }
        if (isContent) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(1, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); //使其能够通过鉴权过滤放行
            filterChain.doFilter(request, response);
            System.out.println("授权");
            return;
        }
        String token = getToken(request);

        //解析token

//TODO:解析token，判断token是否完全过期，完全过期抛出异常重新登录，未完全过期的解析token生成token并进行用户授权，
// 完全有效的token，直接解析token中权限信息进行授权
        //try{


        if (JwtUtil.getExpiredTime(token) < -JwtUtil.JWT_TTL) {
            throw new MyException("token已经完全过期，请重新登录");
        }
        Claims claims = JwtUtil.parseJWT(token);
        Long userid = (Long) claims.get("userid");  //从token获取用户id
        System.out.println("userID"+userid);
        String email = claims.get("email", String.class);
        List<Map<String, String>> Authorities = (List<Map<String, String>>) claims.get("Authorities");//从token获取用户权限列表

        if (JwtUtil.getExpiredTime(token) <= 20 * 60 * 1000L && JwtUtil.getExpiredTime(token) >= -JwtUtil.JWT_TTL) {
            System.out.println("已经生成新的token");
            String newToken = JwtUtil.createJWT(userid,email,Authorities);
            // 将新的token设置到响应头中
            response.setHeader("Authorization", "Bearer " + newToken);
        }

        //TODO 获取用户的权限信息封装到Authentication中
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Map<String, String> authorityMap : Authorities) {
            String authorityValue = authorityMap.get("authority");
            authorities.add(new SimpleGrantedAuthority(authorityValue));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userid, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }

}


