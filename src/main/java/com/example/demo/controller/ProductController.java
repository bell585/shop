package com.example.demo.controller;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.UpdateDescriptionDTO;
import com.example.demo.comment.Result;
import com.example.demo.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/product")

public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductAttributeValueService productAttributeValueService;
    @Autowired
    ProductAttributeService productAttributeService;
    @Autowired
    ProductPriceService productPriceService;
    @Autowired
    ProductPictureService productPictureService;

    @Transactional //启用事务管理
    @PostMapping("/addProduct") //新增商品
    public Result<?> addProduct(@ModelAttribute ProductDTO productDTO) {
        Long productId = productService.insert(productDTO.getProductInfo(),productDTO.getDetailsImages(),productDTO.getDefaultPrice());//向基本信息表插入数据并获得Id
        productAttributeValueService.setAttributeValue(productId, productDTO); //设置商品属性
        productPriceService.insertProductPrice(productId,productDTO.getCspInfo(),productDTO.getCspImages()); //设置对应属性组合对应的价格库存等。
        productPictureService.insertPicture(productId,productDTO.getRotateImages());
        return Result.success(201, "这里是/shop路径返回信息", null );

    }
    @GetMapping("/selectProduct") //商家查询所有商品基本信息（已上架、已下架）
    public Result<?> selectAllProductByShopIdStatus(Long shopId,Integer status,Integer offset, Integer pageSize){
        return Result.success(201, "OK",productService.selectAllProductByShopIdStatus(shopId,status,offset,pageSize));
    }
    @GetMapping("/productDetails") //查询商品详情的图片信息
    public Result<?> selectProductDetailsById(Long productId){
        return Result.success(201, "OK",productService.selectProductDetailsById(productId));
    }
    @PostMapping("/update") //更新商品基本信息
    public Result<?> updateByPrimaryKeySelective(@RequestBody Map<String,Object> updateInfo){
        return Result.success(201, "OK",productService.updateByPrimaryKeySelective(updateInfo));
    }
    @PostMapping("/updateDetails") //更新商品详情（商品详情图片）
    public Result<?> updateDetails(@ModelAttribute UpdateDescriptionDTO updateDescriptionDTO){
        Long productId = updateDescriptionDTO.getProductId();
        List<MultipartFile> detailsImages = updateDescriptionDTO.getDetailsImages();
        List<Integer> index = updateDescriptionDTO.getIndex();
        return Result.success(201, "OK", productService.updateDetails(productId, detailsImages, index));
    }
    @DeleteMapping ("/offShelfProduct") //下架商品
    public Result<?> offShelfProduct(@RequestBody Map<String,Long> offShelfProduct){
        return Result.success(201, "OK",productService.offShelfProduct(offShelfProduct.get("productId")));
    }
    @GetMapping("/selectProducts") //用户通过分类查询在架商品
    public Result<?> selectAllProductByShopIdStatus(Integer categoryId,Integer offset, Integer pageSize){
        return Result.success(201, "OK",productService.selectProductByCategoryId(categoryId,offset,pageSize));
    }
    @GetMapping("/selectProductsByName") //用户通过关键字查询在架商品
    public Result<?> selectProductsByName(String name,Integer offset, Integer pageSize){
        String trimmedName = name.trim();
        return Result.success(201, "OK",productService.selectByName(trimmedName, offset, pageSize));
    }



}
