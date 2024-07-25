package com.example.demo.model;

public class ProductAttributeValue {
    private Long valueid;

    private Long productid;

    private Long attributeid;

    private String value;

    public Long getValueid() {
        return valueid;
    }

    public void setValueid(Long valueid) {
        this.valueid = valueid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Long getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(Long attributeid) {
        this.attributeid = attributeid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}