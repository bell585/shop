package com.example.demo.service.impl;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.service.RecommendProductService;
import com.example.demo.service.SearchHistoryService;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
@Service

public class RecommendProductServiceImpl implements RecommendProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private SearchHistoryService searchHistoryService;
    @Override
    public List<Product> indexRecommendProduct() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println(token);
        Claims claims = JwtUtil.parseJWT(token);
        System.out.println(claims);
        Long userId = (Long) claims.get("userid");
        String keyWords = searchHistoryService.getKeyWords(userId);
        System.out.println(keyWords);
        return productMapper.RecommendProduct(keyWords);

    }
}
