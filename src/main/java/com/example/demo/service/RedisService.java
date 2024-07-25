package com.example.demo.service;

public interface RedisService {
    void saveVerificationCode(String email, String code);//保存至redis

    String getVerificationCode(String email);//从redis获取信息

    boolean verifyCode(String email, String code);
}
