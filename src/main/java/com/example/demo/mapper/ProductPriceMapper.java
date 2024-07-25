package com.example.demo.mapper;

import com.example.demo.model.ProductPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductPriceMapper {
    int deleteByPrimaryKey(Long priceid);

    int insert(ProductPrice row);

    int insertSelective(ProductPrice row);

    ProductPrice selectByPrimaryKey(Long priceid);

    int updateByPrimaryKeySelective(ProductPrice row);

    int updateByPrimaryKey(ProductPrice row);
    ProductPrice selectPictureByPriceId(Long priceId);

    List<ProductPrice> selectAllByProductId(Long productId);
    int updateSortByProductId(Long productId);
    ProductPrice selectPriceByPriceId(Long priceId);
    int updateStock(Long priceId,Integer stock1);
    ProductPrice selectStockByPriceId(Long priceId);
    ProductPrice select(Long priceId);


}