package com.example.demo.mapper;

import com.example.demo.model.ProductAttributeValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper

public interface ProductAttributeValueMapper {
    int deleteByPrimaryKey(Long valueid);

    int insert(ProductAttributeValue row);

    int insertSelective(ProductAttributeValue row);

    ProductAttributeValue selectByPrimaryKey(Long valueid);

    int updateByPrimaryKeySelective(ProductAttributeValue row);

    int updateByPrimaryKey(ProductAttributeValue row);
    ArrayList<ProductAttributeValue> selectByProductIdAttributeId(Long productId, Long attributeId);
    ProductAttributeValue selectByValueId(Long valueId);
}