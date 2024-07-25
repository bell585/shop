package com.example.demo.model;

public class ProductPicture {
    private Long pictureid;

    private Long productid;

    private String picture;

    public Long getPictureid() {
        return pictureid;
    }

    public void setPictureid(Long pictureid) {
        this.pictureid = pictureid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ProductPicture{" +
                "pictureid=" + pictureid +
                ", productid=" + productid +
                ", picture='" + picture + '\'' +
                '}';
    }
}