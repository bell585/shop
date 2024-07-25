package com.example.demo.model;

public class ProductAttribute {
    private Long attributeid;

    private String name;

    private Integer categoryid;

    private String option;

    public Long getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(Long attributeid) {
        this.attributeid = attributeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}