package com.example.demo.controller;

import com.example.demo.DTO.EmailDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.comment.Result;
import com.example.demo.execption.MyException;
import com.example.demo.model.Users;
import com.example.demo.service.MailService;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    RedisService redisService;


    @PostMapping("/mail")
    public Result<?> mail(@Validated @RequestBody EmailDTO emailDTO) {
        mailService.sendMail(emailDTO.getEmail());
        return Result.success(200, "验证码发送成功！", "YES");
    }


    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO registerDTO) {
        if (!(registerDTO.getPassword1().equals(registerDTO.getPassword2()))) {
            throw new MyException("两次密码不一致");
        }
        String code = redisService.getVerificationCode(registerDTO.getEmail());
        String code1 = registerDTO.getCode();
        if (code1.equals(code)) {
            userService.registerByEmail(registerDTO);
            return Result.success(200, "注册成功！", null);
        } else {
            throw new MyException("验证码错误");
        }
    }

    @PostMapping("/login")
    public Result<?> login1(@RequestBody LoginDTO loginDTO) {
        return Result.success(201, "成功返回", userService.login(loginDTO));
    }

    @PostMapping("/updateInfo")
    public Result<?> updateInfo(@RequestBody Map<String, String> updateInfo) {
        userService.updateUserInfo(updateInfo);
        return Result.success(201, "成功返回", null);
    }

    @PostMapping("/updatePsd")
    public Result<?> updatePsd(@Validated @RequestBody RegisterDTO registerDTO) {
        if (!(registerDTO.getPassword1().equals(registerDTO.getPassword2()))) {
            throw new MyException("两次密码不一致");
        }
        String code = redisService.getVerificationCode(registerDTO.getEmail());
        String code1 = registerDTO.getCode();
        if (code1.equals(code)) {
            userService.updatePsd(registerDTO.getPassword1());
            return Result.success(200, "密码修改成功！", null);
        } else {
            throw new MyException("验证码错误");
        }
    }

    @GetMapping("/logout")
    public Result<?> logout() {
        userService.logout();
        return Result.success(201, "注销成功", null);
    }

    @GetMapping("/logoff")
    public Result<?> logff() {
        userService.logoff();
        return Result.success(201, "注销成功", null);
    }

}
