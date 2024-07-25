package com.example.demo.service.impl;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.SearchHistory;
import com.example.demo.service.ProductService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.RedisCache;
import com.example.demo.util.RedisIdWoker;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private RedisIdWoker redisIdWoker;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public Long getProductId(Long shopId, LocalDateTime createTime) {
        return productMapper.getProductId(shopId, createTime).getProductid();
    }

    @Override
    public Long insert(Map<String, String> productInfo, List<MultipartFile> detailsImages, BigDecimal defaultPrice) {
        Product product = new Product();
        product.setProductid(redisIdWoker.nextId("product"));
        product.setName(productInfo.get("name"));
        product.setBrand(productInfo.get("brand"));
        StringBuilder description = new StringBuilder();
        for (MultipartFile file : detailsImages) {
            String newFileName = FileUtil.setImage(file, FileUtil.detailsImages);
            description.append(newFileName).append(",");
        }
        product.setDescription(description.toString());
        product.setCategoryid(Integer.parseInt(productInfo.get("categoryid")));
        product.setPrice(defaultPrice);
        product.setStatus((byte) 0);
        product.setShopid(Integer.parseInt(productInfo.get("shopid")));
        LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
        product.setCreatetime(currentDateTime);
        product.setUpdatetime(currentDateTime);
        productMapper.insert(product);
        return getProductId(Long.valueOf(productInfo.get("shopid")), currentDateTime);

    }

    @Override
    public List<Product> selectAllProductByShopIdStatus(Long shopId, Integer status, Integer offset, Integer pageSize) {

        return productMapper.selectAllProductByShopIdStatus(shopId, status, offset * pageSize, pageSize);
    }

    @Override
    public Product selectProductDetailsById(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        String imageNames = product.getDescription();
        product.setDescription(FileUtil.ImagesFullPath(imageNames));
        return product;
    }

    @Override
    public int updateByPrimaryKeySelective(Map<String, Object> updateInfo) {
        Product product = new Product();
        product.setProductid((Long) updateInfo.get("productId"));
        product.setName((String) updateInfo.get("name"));
        product.setBrand((String) updateInfo.get("brand"));
        product.setUpdatetime(LocalDateTime.now().withNano(0));
        System.out.println(product);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateDetails(Long productId, List<MultipartFile> detailsImages, List<Integer> index) {
        Product product = productMapper.selectByPrimaryKey(productId);
        String description = product.getDescription();
        String[] descriptionArray = description.split(",");
        int cnt = 0;
        StringBuilder descriptionSB = new StringBuilder();
        for (MultipartFile detailsImage : detailsImages) {
            String image = FileUtil.setImage(detailsImage, FileUtil.detailsImages);
            FileUtil.deleteImages(descriptionArray[index.get(cnt) - 1], FileUtil.detailsImages);
            descriptionArray[index.get(cnt) - 1] = image;
            cnt++;
        }
        for (String name : descriptionArray) {
            descriptionSB.append(name).append(",");
        }
        return productMapper.updateDetails(productId, descriptionSB.toString());
    }

    @Override
    public int offShelfProduct(Long productId) {
        Product product = new Product();
        product.setProductid(productId);
        product.setStatus((byte) 1);
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<Product> selectProductByCategoryId(Integer categoryId, Integer offset, Integer pageSize) {
        return productMapper.selectProductByCategoryId(categoryId, offset, pageSize);
    }

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<Product> selectByName(String name, Integer offset, Integer pageSize) {
        List<Product> products = productMapper.selectByName(name, offset, pageSize);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = JwtUtil.parseJWT(token);
        Long userid = (Long) claims.get("userid");
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setUserid(userid);
        searchHistory.setKeywords(name);
        redisCache.setCacheObject(RedisCache.SearchHistory + userid, name, 10, TimeUnit.MINUTES);
        rabbitTemplate.convertAndSend("searchHistory-exchange", "searchHistory.create", searchHistory);
        return products;
    }
}
