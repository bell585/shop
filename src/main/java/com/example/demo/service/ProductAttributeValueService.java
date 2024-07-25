package com.example.demo.service;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.model.ProductAttributeValue;

import java.util.ArrayList;

public interface ProductAttributeValueService {
    ArrayList<ProductAttributeValue> selectByProductIdAttributeId(Long productId,Long attributeId);
    void insertAttributeValue(Long productId,Long attributeId,String value);
    void setAttributeValue(Long productId,ProductDTO productDTO);




}
