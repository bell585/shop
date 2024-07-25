package com.example.demo.service.impl;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.execption.MyException;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.model.LoginUser;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.RedisCache;
import com.example.demo.util.RedisIdWoker;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private RedisIdWoker redisIdWoker;



    @Override
    public void registerByEmail(RegisterDTO registerDTO) {
        Users result = usersMapper.selectAllByEmailUsers(registerDTO.getEmail());
        if (result != null) {
            if(result.getCondition()==1){  //用户状态标志位，0为正常用户，1为注销用户
                //TODO：删除原来所有该用户信息
                System.out.println("YES");
                result.setPassword(registerDTO.getPassword1());
                result.setCondition(0);
                usersMapper.updateByPrimaryKey(result);
                return;
            }
            else{
                throw new MyException("用户已存在，无法注册");
            }

        }
        Users newUsers = new Users();
        newUsers.setUserid(redisIdWoker.nextId("user"));
        newUsers.setEmail(registerDTO.getEmail());
        newUsers.setPassword(registerDTO.getPassword1());
        newUsers.setPhone("");
        newUsers.setUsertypeid(0);
        usersMapper.insert(newUsers);
    }

    @Override
    public Map<String, String> login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        //AuthenticationManager委托机制对authenticationToken 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没有通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        LoginUser user = (LoginUser) authenticate.getPrincipal();
        String email = user.getEmail();
        Long userid = user.getUserid();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        String token = JwtUtil.createJWT(userid,email, authorities);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization","Bearer " + token);
        redisCache.setCacheObject(RedisCache.Token + email, user);
        return map;

    }
    @Override
    public void updateUserInfo(Map<String, String> updateInfo) {
        Users result = usersMapper.selectByPrimaryKey((Long) JwtUtil.getClaims().get("userid"));
        result.setUsername(updateInfo.get("username"));
        result.setPhone(updateInfo.get("phone"));
        result.setSignature(updateInfo.get("signature"));
        usersMapper.updateByPrimaryKey(result);
    }

    @Override
    public void updatePsd(String psd) {
        Users result = usersMapper.selectByPrimaryKey((Long) JwtUtil.getClaims().get("userid"));
        result.setPassword(psd);
        usersMapper.updateByPrimaryKey(result);
        return ;
    }

    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        redisCache.addToBlacklist("Authorization blacklist", authorizationHeader,120,TimeUnit.MINUTES);
    }




    @Override
    public void logoff() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        String token  =authorizationHeader.replace("Bearer ","");
        Claims claims = JwtUtil.parseJWT(token);
        Long userid = (Long) claims.get("userid");
        Users result = usersMapper.selectByPrimaryKey(userid);
        result.setCondition(1);
        usersMapper.updateByPrimaryKey(result);
        System.out.println(result.getEmail());
        redisCache.addToBlacklist("Authorization blacklist", authorizationHeader,120,TimeUnit.MINUTES);



    }
}
