package com.example.demo.service;

import com.example.demo.model.ProductAttribute;

import java.util.List;

public interface ProductAttributeService {
    Long selectIdByNameCategoryId(Integer categoryId, String name);
    List<ProductAttribute> selectIdNameByCategoryId(Integer categoryId);
}
