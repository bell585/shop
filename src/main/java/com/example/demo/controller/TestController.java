package com.example.demo.controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.UpdateDescriptionDTO;
import com.example.demo.comment.Result;
import com.example.demo.model.Product;
import com.example.demo.service.*;
import com.example.demo.util.FileUtil;
import com.example.demo.util.RedisCache;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {
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
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    OrderService orderService;
    @Autowired
    RedisCache redisCache;
    @Autowired
    private RecommendProductService recommendProductService;

    @GetMapping("/{categoryId}")
    public Result<?> getThirdCategories(@PathVariable Integer categoryId) {
        return Result.success(201, "成功返回", productCategoryService.selectAllByParentId(categoryId));
    }
    @PostMapping("/createOrder")
    public Result<?> selectByProductIdAttributeId(@RequestBody OrderDTO orderDTO) {
        return Result.success(201, "OK", orderService.createOrder(orderDTO));
    }
    @GetMapping("/selectProductsByName") //用户通过关键字查询在架商品
    public Result<?> selectProductsByName(String name,Integer offset, Integer pageSize){
        return Result.success(201, "OK",productService.selectByName(name,offset,pageSize));
    }
    @GetMapping("/selectAllByProductId") //用户通过关键字查询在架商品
    public Result<?> selectAllByProductId(Long productId){
        return Result.success(201, "OK",productPriceService.selectAllByProductId(productId));
    }@GetMapping("/indexRecommendProduct")
    public Result<?> indexRecommendProduct(){
        return Result.success(201, "OK",recommendProductService.indexRecommendProduct());
    }


}
