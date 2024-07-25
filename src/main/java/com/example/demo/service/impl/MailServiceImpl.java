package com.example.demo.service.impl;

import com.example.demo.service.MailService;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service

public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisService redisService;


    @Override
    public String generateRandomCode() {//随机生成验证码
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public void sendMail(String email) {//发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ys5087cns@163.com");
        message.setTo(email);
        message.setSubject("购物系统验证码。");
        String code = generateRandomCode();
        message.setText("您最新的验证码是: " + code + ",有效期5小时。");
        redisService.saveVerificationCode(email, code);
        mailSender.send(message);

    }


}
