package com.example.demo.mapper;

import com.example.demo.model.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryid);

    int insert(ProductCategory row);

    int insertSelective(ProductCategory row);

    ProductCategory selectByPrimaryKey(Integer categoryid);

    int updateByPrimaryKeySelective(ProductCategory row);

    int updateByPrimaryKey(ProductCategory row);
    List<ProductCategory> selectAllByParentId(Integer categoryId);
}