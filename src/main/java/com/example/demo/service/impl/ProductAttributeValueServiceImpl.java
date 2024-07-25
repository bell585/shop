package com.example.demo.service.impl;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.mapper.ProductAttributeValueMapper;
import com.example.demo.model.ProductAttributeValue;
import com.example.demo.service.ProductAttributeService;
import com.example.demo.service.ProductAttributeValueService;
import com.example.demo.util.RedisIdWoker;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {
    @Resource
    private ProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Resource
    private RedisIdWoker redisIdWoker;
    @Override
    public ArrayList<ProductAttributeValue> selectByProductIdAttributeId(Long productId,Long attributeId) {
        return productAttributeValueMapper.selectByProductIdAttributeId(productId, attributeId);
    }
    @Override
    public void insertAttributeValue(Long productId, Long attributeId, String value) {
        ProductAttributeValue pav = new ProductAttributeValue();
        pav.setValueid(redisIdWoker.nextId("productAttributeValue"));
        pav.setProductid(productId);
        pav.setAttributeid(attributeId);
        pav.setValue(value);
        productAttributeValueMapper.insert(pav);

    }

    @Override
    public void setAttributeValue(Long productId, ProductDTO productDTO) {
        int categoryId = Integer.parseInt(productDTO.getProductInfo().get("categoryid"));
        for(String attributeName: productDTO.getAttributeInfo().keySet()){
            Long attributeId = productAttributeService.selectIdByNameCategoryId(categoryId,attributeName);
            List<String> attributeValues = productDTO.getAttributeInfo().get(attributeName);
            for(String attributeValue: attributeValues){
                insertAttributeValue(productId,attributeId,attributeValue);
            }
        }
    }


}
