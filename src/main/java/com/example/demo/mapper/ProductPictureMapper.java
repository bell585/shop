package com.example.demo.mapper;

import com.example.demo.model.ProductPicture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface ProductPictureMapper {
    int deleteByPrimaryKey(Long pictureid);

    int insert(ProductPicture row);

    int insertSelective(ProductPicture row);

    ProductPicture selectByPrimaryKey(Long pictureid);

    int updateByPrimaryKeySelective(ProductPicture row);

    int updateByPrimaryKey(ProductPicture row);
    List<ProductPicture> selectAllByProductId(Long productId);
    ProductPicture selectPictureByPictureId(Long pictureId);
}