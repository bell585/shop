package com.example.demo.model;

import java.math.BigDecimal;

public class ProductPrice {
    private Long priceid;

    private Long productid;

    private String attributevalueids;

    private BigDecimal price;

    private String picture;

    private Integer stock;

    private Byte sort;

    public Long getPriceid() {
        return priceid;
    }

    public void setPriceid(Long priceid) {
        this.priceid = priceid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getAttributevalueids() {
        return attributevalueids;
    }

    public void setAttributevalueids(String attributevalueids) {
        this.attributevalueids = attributevalueids;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "priceid=" + priceid +
                ", productid=" + productid +
                ", attributevalueids='" + attributevalueids + '\'' +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", stock=" + stock +
                ", sort=" + sort +
                '}';
    }
}