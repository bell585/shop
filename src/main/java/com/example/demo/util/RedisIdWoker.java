package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component

public class RedisIdWoker {
    private static final Long BeginTimeStamp =1698796800L;
    private static final int CountBits = 32;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public long nextId(String keyPrefix){
        LocalDateTime now = LocalDateTime.now();
        long nowTime = now.toEpochSecond(ZoneOffset.UTC);
        Long timeStamp = nowTime-BeginTimeStamp;
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        String key = "inc" + keyPrefix + ":" + date;
        Long count = stringRedisTemplate.opsForValue().increment(key);
        stringRedisTemplate.expire(key,24*60*60, TimeUnit.SECONDS);
        System.out.println(count);
        System.out.println(timeStamp);
        return timeStamp << CountBits | count;

    }


}

