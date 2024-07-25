package com.example.demo.mapper;

import com.example.demo.model.ProductShop;

public interface ProductShopMapper {
    int deleteByPrimaryKey(Long shopid);

    int insert(ProductShop row);

    int insertSelective(ProductShop row);

    ProductShop selectByPrimaryKey(Long shopid);

    int updateByPrimaryKeySelective(ProductShop row);

    int updateByPrimaryKey(ProductShop row);
}