package com.example.demo.service;

public interface MailService {
    String generateRandomCode();//随机生成验证码

    void sendMail(String email);//邮件发送


}
