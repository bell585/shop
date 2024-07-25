package com.example.demo.service.impl;

import com.example.demo.mapper.ProductAttributeValueMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.ProductPriceMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ProductAttributeValue;
import com.example.demo.model.ProductPrice;
import com.example.demo.service.ProductPriceService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.RedisCache;
import com.example.demo.util.RedisIdWoker;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service

public class ProductPriceServiceImpl implements ProductPriceService {
    @Resource
    private ProductPriceMapper productPriceMapper;
    @Resource
    private ProductAttributeValueMapper productAttributeValueMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RedisIdWoker redisIdWoker;

    @Override
    public void insertProductPrice(Long productId, Map<String, List<String>> cspInfo, List<MultipartFile> cspImages) {
        int cnt = 0;
        for (String csp : cspInfo.keySet()) {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setPriceid(redisIdWoker.nextId("productPrice"));
            productPrice.setProductid(productId);
            productPrice.setAttributevalueids(csp);
            List<String> productPriceInfo = cspInfo.get(csp);
            productPrice.setPrice(new BigDecimal(productPriceInfo.get(0)));
            productPrice.setStock(Integer.parseInt(productPriceInfo.get(1)));
            productPrice.setSort(Byte.parseByte(productPriceInfo.get(2)));
            MultipartFile file = cspImages.get(cnt);
            String newFileName = FileUtil.setImage(file, FileUtil.cspImages);
            productPrice.setPicture(newFileName);
            productPriceMapper.insert(productPrice);
            cnt++;
        }
    }


    @Override
    public List<ProductPrice> selectAllByProductId(Long productId) {
        List<ProductPrice> productPrices = productPriceMapper.selectAllByProductId(productId);
        for (ProductPrice productPrice : productPrices) {
            String[] AttributeValueIds = productPrice.getAttributevalueids().split(",");
            StringBuilder attributeValues = new StringBuilder();
            for (String attributeValueId : AttributeValueIds) {
                ProductAttributeValue attributeValue = productAttributeValueMapper.selectByValueId(Long.valueOf(attributeValueId));
                attributeValues.append(attributeValue.getValue()).append(",");
            }
            attributeValues.deleteCharAt(attributeValues.length() - 1);
            productPrice.setAttributevalueids(attributeValues.toString());
            redisCache.setCacheObject("price:"+productPrice.getPriceid(),productPrice,15, TimeUnit.MINUTES);

        }
        return productPrices;
    }

    @Override
    public int updateByPrimaryKeySelective(Long priceId, BigDecimal price, MultipartFile picture, Integer stock) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPriceid(priceId);
        productPrice.setPrice(price);
        String pictureName = FileUtil.setImage(picture, FileUtil.cspImages);
        productPrice.setPicture(pictureName);
        ProductPrice oldProductPrice = productPriceMapper.selectPictureByPriceId(priceId);
        String oldPicture = oldProductPrice.getPicture();
        FileUtil.deleteImages(oldPicture, FileUtil.cspImages);
        productPrice.setStock(stock);
        return productPriceMapper.updateByPrimaryKeySelective(productPrice);
    }

    @Override
    @Transactional
    public int updateDefault(Long priceId, Long productId) {
        productPriceMapper.updateSortByProductId(productId);
        ProductPrice productPrice = productPriceMapper.selectPriceByPriceId(priceId);
        productPrice.setSort((byte) 1);
        productPrice.setPriceid(priceId);
        Product product = new Product();
        product.setProductid(productId);
        product.setPrice(productPrice.getPrice());
        productMapper.updateByPrimaryKeySelective(product);
        return productPriceMapper.updateByPrimaryKeySelective(productPrice);
    }
}
