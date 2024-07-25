package com.example.demo.controller;

import com.example.demo.comment.Result;
import com.example.demo.service.ProductAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attributeValue")
public class ProductAttributeValueController {
    @Autowired
    ProductAttributeValueService productAttributeValueService;
    @GetMapping("/selectAttributeValue") //用户通过关键字查询在架商品
    public Result<?> selectByProductIdAttributeId(Long productId,Long attributeId){
        return Result.success(201, "OK",productAttributeValueService.selectByProductIdAttributeId(productId, attributeId));
    }

}
