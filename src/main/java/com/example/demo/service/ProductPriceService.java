package com.example.demo.service;

import com.example.demo.model.ProductPrice;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductPriceService {
    void insertProductPrice(Long productId, Map<String,List<String>>cspInfo,List<MultipartFile> cspImages);
    List<ProductPrice> selectAllByProductId(Long productId);
    int updateByPrimaryKeySelective(Long priceId, BigDecimal price, MultipartFile picture, Integer stock);
    int updateDefault(Long priceId,Long productId);



}
