package com.example.demo.model;

import java.util.Date;

public class ProductShop {
    private Long shopid;

    private String shopname;

    private Integer userid;

    private Double productdescribescore;

    private Double sellerservicescore;

    private Double logisticsscore;

    private Date createtime;

    private String signature;

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Double getProductdescribescore() {
        return productdescribescore;
    }

    public void setProductdescribescore(Double productdescribescore) {
        this.productdescribescore = productdescribescore;
    }

    public Double getSellerservicescore() {
        return sellerservicescore;
    }

    public void setSellerservicescore(Double sellerservicescore) {
        this.sellerservicescore = sellerservicescore;
    }

    public Double getLogisticsscore() {
        return logisticsscore;
    }

    public void setLogisticsscore(Double logisticsscore) {
        this.logisticsscore = logisticsscore;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}