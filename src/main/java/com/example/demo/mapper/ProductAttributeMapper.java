package com.example.demo.mapper;

import com.example.demo.model.ProductAttribute;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface ProductAttributeMapper {
    int deleteByPrimaryKey(Long attributeid);

    int insert(ProductAttribute row);

    int insertSelective(ProductAttribute row);

    ProductAttribute selectByPrimaryKey(Long attributeid);

    int updateByPrimaryKeySelective(ProductAttribute row);

    int updateByPrimaryKey(ProductAttribute row);
    ProductAttribute selectIdByNameCategoryId(Integer categoryId, String name);
    List<ProductAttribute> selectIdNameByCategoryId(Integer categoryId);
}