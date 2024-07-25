package com.example.demo.controller;

import com.example.demo.comment.Result;
import com.example.demo.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")

public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/{categoryId}") //获取子类型
    public Result<?> getThirdCategories(@PathVariable Integer categoryId) {
        return Result.success(201, "成功返回", productCategoryService.selectAllByParentId(categoryId));
    }
}
