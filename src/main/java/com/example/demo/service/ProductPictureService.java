package com.example.demo.service;

import com.example.demo.model.ProductPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductPictureService {
    void insertPicture(Long productId, List<MultipartFile> rotateImages);

    List<ProductPicture> selectAllByProductId(Long productId);
    int deleteByPictureId(Long pictureId);
    int updateByPictureId(Long pictureId,MultipartFile picture);
}
