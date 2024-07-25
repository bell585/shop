package com.example.demo.service.impl;

import com.example.demo.mapper.ProductAttributeMapper;
import com.example.demo.model.ProductAttribute;
import com.example.demo.service.ProductAttributeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    @Resource
    private ProductAttributeMapper productAttributeMapper;
    @Override
    public Long selectIdByNameCategoryId(Integer categoryId, String name) {
        return productAttributeMapper.selectIdByNameCategoryId(categoryId, name).getAttributeid();
    }

    @Override
    public List<ProductAttribute> selectIdNameByCategoryId(Integer categoryId) {
        return productAttributeMapper.selectIdNameByCategoryId(categoryId);
    }
}
