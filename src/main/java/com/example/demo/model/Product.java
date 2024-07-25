package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class Product {
    private Long productid;

    private String name;

    private String brand;

    private Integer categoryid;

    private BigDecimal price;

    private Byte status;

    private Integer shopid;

    private LocalDateTime createtime;

    private LocalDateTime updatetime;

    private String description;
    private Integer shopId;
    private String shopName;
    private Integer count;


    public Product() {
    }

    public Product(Long productid, String name, String brand, Integer categoryid, BigDecimal price, Byte status, Integer shopid, LocalDateTime createtime, LocalDateTime updatetime, String description, Integer shopId, String shopName, Integer count) {
        this.productid = productid;
        this.name = name;
        this.brand = brand;
        this.categoryid = categoryid;
        this.price = price;
        this.status = status;
        this.shopid = shopid;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.description = description;
        this.shopId = shopId;
        this.shopName = shopName;
        this.count = count;
    }

    /**
     * 获取
     * @return productid
     */
    public Long getProductid() {
        return productid;
    }

    /**
     * 设置
     * @param productid
     */
    public void setProductid(Long productid) {
        this.productid = productid;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取
     * @return categoryid
     */
    public Integer getCategoryid() {
        return categoryid;
    }

    /**
     * 设置
     * @param categoryid
     */
    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 获取
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取
     * @return shopid
     */
    public Integer getShopid() {
        return shopid;
    }

    /**
     * 设置
     * @param shopid
     */
    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    /**
     * 获取
     * @return createtime
     */
    public LocalDateTime getCreatetime() {
        return createtime;
    }

    /**
     * 设置
     * @param createtime
     */
    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取
     * @return updatetime
     */
    public LocalDateTime getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置
     * @param updatetime
     */
    public void setUpdatetime(LocalDateTime updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取
     * @return shopId
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置
     * @param shopId
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取
     * @return shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置
     * @param shopName
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    public String toString() {
        return "Product{productid = " + productid + ", name = " + name + ", brand = " + brand + ", categoryid = " + categoryid + ", price = " + price + ", status = " + status + ", shopid = " + shopid + ", createtime = " + createtime + ", updatetime = " + updatetime + ", description = " + description + ", shopId = " + shopId + ", shopName = " + shopName + ", count = " + count + "}";
    }
}