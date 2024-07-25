package com.example.demo.DTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class ProductDTO {

    private Map<String, String> productInfo; //基本信息
    private List<MultipartFile> detailsImages;//详细信息部分图片列表

    private Map<String,List<String> > attributeInfo; //商品属性信息
    private Map<String,List<String>>cspInfo; //属性组合对应属性值
    private List<MultipartFile> cspImages;
    private BigDecimal defaultPrice; //默认基础价格
    private List<MultipartFile> rotateImages; //商品轮播图片
    public Map<String, String> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = JSON.parseObject(productInfo, new TypeReference<Map<String, String>>() {});
    }

    public List<MultipartFile> getDetailsImages() {
        return detailsImages;
    }

    public void setDetailsImages(List<MultipartFile> detailsImages) {
        this.detailsImages = detailsImages;
    }

    public List<MultipartFile> getRotateImages() {
        return rotateImages;
    }

    public void setRotateImages(List<MultipartFile> rotateImages) {
        this.rotateImages = rotateImages;
    }

    public Map<String, List<String>> getAttributeInfo() {
        return attributeInfo;
    }

    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = JSON.parseObject(attributeInfo, new TypeReference<Map<String, List<String>>>() {});
    }

    public Map<String, List<String>> getCspInfo() {
        return cspInfo;
    }

    public void setCspInfo(String cspInfo) {
        this.cspInfo = JSON.parseObject(cspInfo, new TypeReference<Map<String, List<String>>>() {});
    }

    public List<MultipartFile> getCspImages() {
        return cspImages;
    }

    public void setCspImages(List<MultipartFile> cspImages) {
        this.cspImages = cspImages;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }
}