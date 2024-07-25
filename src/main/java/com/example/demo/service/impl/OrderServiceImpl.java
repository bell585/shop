package com.example.demo.service.impl;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.execption.MyException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductPriceMapper;
import com.example.demo.model.Order;
import com.example.demo.model.ProductPrice;
import com.example.demo.service.OrderService;
import com.example.demo.util.RedisCache;
import com.example.demo.util.RedisIdWoker;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service

public class OrderServiceImpl implements OrderService {
    @Resource
    private ProductPriceMapper productPriceMapper;
    @Resource
    private RedisIdWoker redisIdWoker;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource

    RedissonClient redissonClient;

    @Override
    public Long createOrder(OrderDTO orderDTO) {
        Long priceId = orderDTO.getPriceId();
        ProductPrice productPrice = redisCache.getCacheObject(RedisCache.ProductPrice + priceId);
        if (productPrice == null) {
            productPrice = productPriceMapper.select(priceId);
            if (productPrice == null) {
                throw new MyException("商品价格不存在");
            }
            redisCache.setCacheObject(RedisCache.ProductPrice + priceId, productPrice, 5, TimeUnit.MINUTES);
        }
        Integer number = orderDTO.getCount();
        Integer stock = productPrice.getStock();
        if (stock < number) throw new MyException("库存不足");
        RLock lock = redissonClient.getLock(RedisCache.ProductPriceLock + priceId);
        lock.lock();
        try {
            productPrice = redisCache.getCacheObject(RedisCache.ProductPrice + priceId);
            if (productPrice == null || productPrice.getStock() < number) {
                throw new MyException("库存不足");
            }
            System.out.println("库存为：" + productPrice.getStock());
            productPrice.setStock(productPrice.getStock() - number);
            if (!redisCache.updateCacheObject(RedisCache.ProductPrice + priceId, productPrice)) {
                throw new MyException("系统内部异常");
            }

        } finally {
            lock.unlock();
        }
        long orderId = redisIdWoker.nextId("order");
        Order order = new Order();
        order.setOrderid(orderId);
        order.setUserid(orderDTO.getUserId());
        order.setProductid(orderDTO.getProductId());
        order.setTotalprice(orderDTO.getTotalPrice());
        order.setActualprice(orderDTO.getActualPrice());
        LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
        order.setCreatetime(currentDateTime);
        order.setPriceid(priceId);
        order.setAddress(orderDTO.getAddress());
        order.setCount(number);
        order.setDiscountedprice(orderDTO.getDiscountedPrice());
        order.setStatus((byte) 0);
        redisCache.setCacheObject(RedisCache.Order + orderId, order, 45, TimeUnit.MINUTES);
        redisCache.addCacheSet(RedisCache.UnpaidOrders+orderDTO.getUserId(),orderId,15,TimeUnit.MINUTES);
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(45);
        rabbitTemplate.convertAndSend("order-exchange", "order.create", order);
        // 发送订单到死信队列，设置过期时间为订单的过期时间
        rabbitTemplate.convertAndSend("cancel-order-exchange", "cancel.order", order, message -> {
            message.getMessageProperties().setExpiration(String.valueOf(Duration.between(LocalDateTime.now(), expireTime).toMillis()));
            return message;
        });
        return orderId;

    }


//    public void processOrder(Order order) {
//        int maxRetries = 3;
//        int retryCount = 0;
//        boolean success = false;
//
//        while (retryCount < maxRetries && !success) {
//            try {
//                order.setStatus((byte) 0);
//                int result = productPriceMapper.updateStock(order.getPriceid(), order.getCount());
//                if (result != 1) {
//                    throw new MyException("更新失败");
//                }
//                orderMapper.insertSelective(order);
//                redisCache.setCacheObject("order:" + order.getOrderid(), order, 15, TimeUnit.MINUTES);
//                success = true;
//            } catch (MyException e) {
//                retryCount++;
//                if (retryCount >= maxRetries) {
//                    throw e;
//                }
//            } catch (Exception e) {
//                retryCount++;
//                if (retryCount >= maxRetries) {
//                    System.out.println(("数据库操作失败，重试次数已达上限"));
//                }
//            }
//        }
//    }
}
