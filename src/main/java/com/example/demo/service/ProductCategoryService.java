package com.example.demo.service;



import com.example.demo.model.ProductCategory;

import java.util.List;


public interface ProductCategoryService {
    List<ProductCategory> selectAllByParentId(Integer categoryId);
}
