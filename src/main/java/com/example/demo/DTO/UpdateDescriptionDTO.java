package com.example.demo.DTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UpdateDescriptionDTO {
    private Long productId;
    private List<MultipartFile> detailsImages;
    private List<Integer> index;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<MultipartFile> getDetailsImages() {
        return detailsImages;
    }

    public void setDetailsImages(List<MultipartFile> detailsImages) {
        this.detailsImages = detailsImages;
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = JSON.parseObject(index, new TypeReference<List<Integer>>() {});
    }
}
