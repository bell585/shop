package com.example.demo.service.impl;

import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveVerificationCode(String email, String code) {//保存信息至Redis数据库
        String key = "email_verification:" + email;
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.HOURS);

    }

    @Override
    public String getVerificationCode(String email) {//从redis中获取验证码
        String key = "email_verification:" + email;
        return redisTemplate.opsForValue().get(key);

    }

    @Override
    public boolean verifyCode(String email, String code) {

        return false;
    }
}
