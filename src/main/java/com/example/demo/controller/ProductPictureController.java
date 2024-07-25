package com.example.demo.controller;

import com.example.demo.comment.Result;
import com.example.demo.service.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/productPicture")

public class ProductPictureController {
    @Autowired
    ProductPictureService productPictureService;

    @PostMapping("/insertPicture")  //新增商品详情界面轮播图片
    public Result<?> insertPicture(Long productId, List<MultipartFile> rotateImages) {
        productPictureService.insertPicture(productId, rotateImages);
        return Result.success(201, "成功返回", null);
    }

    @GetMapping("/selectPicture") //查询商品轮播图片
    public Result<?> selectAllByProductId(Long productId) {
        return Result.success(201, "成功返回", productPictureService.selectAllByProductId(productId));
    }

    @DeleteMapping("/deletePicture") //删除商品轮播图片
    public Result<?> deletePicture(@RequestBody Map<String, Long> deletePicture) {
        return Result.success(201, "成功返回", productPictureService.deleteByPictureId(deletePicture.get("pictureId")));
    }

    @PostMapping("/updatePicture") //更新商品轮播图片
    public Result<?> updatePicture(Long pictureId, MultipartFile picture) {
        return Result.success(201, "成功返回", productPictureService.updateByPictureId(pictureId, picture));
    }
}
