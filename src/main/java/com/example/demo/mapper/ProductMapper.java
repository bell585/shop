package com.example.demo.mapper;

import com.example.demo.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

import java.time.LocalDateTime;
import java.util.List;

@Mapper

public interface ProductMapper {
    int deleteByPrimaryKey(Long productid);

    int insert(Product row);

    int insertSelective(Product row);

    Product selectByPrimaryKey(Long productid);

    int updateByPrimaryKeySelective(Product row);

    int updateByPrimaryKeyWithBLOBs(Product row);

    int updateByPrimaryKey(Product row);
    Product getProductId(Long shopId, LocalDateTime createTime);
    Product selectByShopIdName(Long shopId,String name);
    //商家查看自己商品
    List<Product> selectAllProductByShopIdStatus(Long shopId,Integer status,Integer offset, Integer pageSize);
    int updateDetails(Long productId, String description);
    Product selectPriceByProductId(Long productId);
    List<Product> selectProductByCategoryId(Integer categoryId,Integer offset, Integer pageSize);
    List<Product> selectByName(String name,Integer offset, Integer pageSize);
    List<Product> RecommendProduct(String keyWords);
}