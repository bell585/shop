package com.example.demo.service.impl;

import com.example.demo.execption.MyException;
import com.example.demo.mapper.ProductPictureMapper;
import com.example.demo.model.ProductPicture;
import com.example.demo.service.ProductPictureService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.RedisIdWoker;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service

public class ProductPictureServiceImpl implements ProductPictureService {
    @Resource
    private ProductPictureMapper productPictureMapper;
    @Resource
    private RedisIdWoker redisIdWoker;

    @Override
    public void insertPicture(Long productId, List<MultipartFile> rotateImages) {
        for (MultipartFile file : rotateImages) {
            ProductPicture productPicture = new ProductPicture();
            productPicture.setPictureid(redisIdWoker.nextId("productPicture"));
            productPicture.setProductid(productId);
            String newFileName = FileUtil.setImage(file, FileUtil.rotateImages);
            productPicture.setPicture(newFileName);
            productPictureMapper.insert(productPicture);

        }
    }

    @Override
    public List<ProductPicture> selectAllByProductId(Long productId) {
        List<ProductPicture> productPictures = productPictureMapper.selectAllByProductId(productId);
        for(ProductPicture productPicture : productPictures){
            String fullPath = FileUtil.rotateImagesPath+productPicture.getPicture();
            productPicture.setPicture(fullPath);
        }
        return productPictures;
    }

    @Override
    public int deleteByPictureId(Long pictureId) {
        String picture = productPictureMapper.selectPictureByPictureId(pictureId).getPicture();
        int i = productPictureMapper.deleteByPrimaryKey(pictureId);
        FileUtil.deleteImages(picture,FileUtil.rotateImages);
        return i;
    }

    @Override
    public int updateByPictureId(Long pictureId,MultipartFile picture) {
        ProductPicture productPicture = productPictureMapper.selectPictureByPictureId(pictureId);
        System.out.println(productPicture);
        String oldPicture = productPicture.getPicture();
        productPicture.setPictureid(pictureId);
        productPicture.setPicture(FileUtil.setImage(picture, FileUtil.rotateImages));
        int i = productPictureMapper.updateByPrimaryKeySelective(productPicture);
        FileUtil.deleteImages(oldPicture,FileUtil.rotateImages);
        return i;
    }
}
