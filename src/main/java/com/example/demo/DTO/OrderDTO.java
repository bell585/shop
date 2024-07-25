package com.example.demo.DTO;



import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private Long userId;
    private Long productId;
    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
    private Long priceId;
    private String address;
    private Integer count;
    private BigDecimal discountedPrice;


    public OrderDTO() {
    }

    public OrderDTO(Long userId, Long productId, BigDecimal totalPrice, BigDecimal actualPrice, Long priceId, String address, Integer count, BigDecimal discountedPrice) {
        this.userId = userId;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.actualPrice = actualPrice;
        this.priceId = priceId;
        this.address = address;
        this.count = count;
        this.discountedPrice = discountedPrice;
    }

    /**
     * 获取
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取
     * @return totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置
     * @param totalPrice
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取
     * @return actualPrice
     */
    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    /**
     * 设置
     * @param actualPrice
     */
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * 获取
     * @return priceId
     */
    public Long getPriceId() {
        return priceId;
    }

    /**
     * 设置
     * @param priceId
     */
    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    /**
     * 获取
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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

    /**
     * 获取
     * @return discountedPrice
     */
    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    /**
     * 设置
     * @param discountedPrice
     */
    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String toString() {
        return "OrderDTO{userId = " + userId + ", productId = " + productId + ", totalPrice = " + totalPrice + ", actualPrice = " + actualPrice + ", priceId = " + priceId + ", address = " + address + ", count = " + count + ", discountedPrice = " + discountedPrice + "}";
    }
}
