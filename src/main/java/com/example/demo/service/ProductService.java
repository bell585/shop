package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Long insert(Map<String, String> productInfo, List<MultipartFile> detailsImages,BigDecimal defaultPrice);
    Long getProductId(Long shopId, LocalDateTime createTime);
    List<Product> selectAllProductByShopIdStatus(Long shopId, Integer status, Integer offset, Integer pageSize);
    Product selectProductDetailsById(Long productId);
    int updateByPrimaryKeySelective(Map<String,Object> updateInfo);
    int updateDetails(Long productId, List<MultipartFile> detailsImages,List<Integer> index);
    int offShelfProduct(Long productId);
    List<Product> selectProductByCategoryId(Integer categoryId,Integer offset, Integer pageSize);
    List<Product> selectByName(String name,Integer offset, Integer pageSize);
}
