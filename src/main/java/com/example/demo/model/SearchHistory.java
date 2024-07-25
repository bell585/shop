package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;

public class SearchHistory {
    private Long searchid;

    private Long userid;

    private String keywords;

    private LocalDateTime createtime;

    public Long getSearchid() {
        return searchid;
    }

    public void setSearchid(Long searchid) {
        this.searchid = searchid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }
}