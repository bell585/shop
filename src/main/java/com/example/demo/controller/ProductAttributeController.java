package com.example.demo.controller;

import com.example.demo.comment.Result;
import com.example.demo.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attribute")

public class ProductAttributeController {
    @Autowired
    ProductAttributeService productAttributeService;
    @GetMapping("/selectAttribute") //查询商品有哪几类属性
    public Result<?> selectIdNameByCategoryId(Integer categoryId){
        return Result.success(201, "OK",productAttributeService.selectIdNameByCategoryId(categoryId));
    }
}
