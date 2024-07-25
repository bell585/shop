package com.example.demo.service.impl;

import com.example.demo.mapper.ProductCategoryMapper;
import com.example.demo.model.ProductCategory;
import com.example.demo.service.ProductCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Resource
    ProductCategoryMapper productCategoryMapper;
    @Override
    public List<ProductCategory> selectAllByParentId(Integer categoryId) {
        return productCategoryMapper.selectAllByParentId(categoryId);
    }
}
