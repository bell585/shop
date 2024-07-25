package com.example.demo.service.impl;

import com.example.demo.mapper.SearchHistoryMapper;
import com.example.demo.service.SearchHistoryService;
import com.example.demo.util.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service

public class SearchHistoryServiceImpl implements SearchHistoryService {
    @Resource
    private SearchHistoryMapper searchHistoryMapper;
    @Resource
    private RedisCache redisCache;


    public String getKeyWords(Long userId) {
        String keyWords = redisCache.getCacheObject(RedisCache.SearchHistory + userId);
        if (keyWords == null) {
            keyWords = searchHistoryMapper.selectByUserid(userId).getKeywords();
        }
        return keyWords;
    }
}
