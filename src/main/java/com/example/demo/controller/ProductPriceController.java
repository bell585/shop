package com.example.demo.controller;

import com.example.demo.comment.Result;
import com.example.demo.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/productPrice")
public class ProductPriceController {
    @Autowired
    ProductPriceService productPriceService;
    @PostMapping("/updatePrice") //更新商品属性组合下的商品价格等SKU
    public Result<?> updatePrice(Long priceId, BigDecimal price, MultipartFile picture, Integer stock){
        return Result.success(201, "成功返回", productPriceService.updateByPrimaryKeySelective(priceId, price, picture, stock));
    }@PostMapping("/updateDefault") //更改默认价格
    public Result<?> updateDefault(@RequestBody Map<String,Long> values){
        return Result.success(201, "成功返回", productPriceService.updateDefault(values.get("priceId"),values.get("productId")));
    }
    @GetMapping("/getPrice") //查询商品所有SKU
    public Result<?> getPrice(Long productId){
        return Result.success(201, "成功返回", productPriceService.selectAllByProductId(productId));
    }
}
