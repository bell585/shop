package com.example.demo.listener;

import com.example.demo.execption.MyException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductPriceMapper;
import com.example.demo.mapper.SearchHistoryMapper;
import com.example.demo.model.Order;
import com.example.demo.model.SearchHistory;
import com.example.demo.util.RedisCache;
import com.example.demo.util.RedisIdWoker;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class RabbitListener {
    @Resource
    private ProductPriceMapper productPriceMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private SearchHistoryMapper searchHistoryMapper;
    @Resource
    private RedisIdWoker redisIdWoker;

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "order-queue")
    @Transactional
    public void processOrder(Order order) {
        Order order1 = redisCache.getCacheObject(RedisCache.Order + order.getOrderid());
        if (order1 == null) {
            System.out.println("发现超时未处理的订单");
            redisCache.setCacheObject(RedisCache.Order + order.getOrderid(), order, 15, TimeUnit.MINUTES);
        }
        if (orderMapper.selectByPrimaryKey(order.getOrderid()) != null) {
            System.out.println("订单已入库");
            return;
        }
        Integer stock = productPriceMapper.selectStockByPriceId(order.getPriceid()).getStock();
        if (stock < order.getCount()) {
            System.out.println("库存不足，操作失败");
            return;
        }
        int result = productPriceMapper.updateStock(order.getPriceid(), order.getCount());
        if (result != 1) {
            System.out.println("扣减库存失败");
            throw new MyException("更新失败");
        }
        orderMapper.insertSelective(order);
    }

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "dead-letter-queue")
    public void cancelOrder(Order order) {
        Order order1 = redisCache.getCacheObject(RedisCache.Order + order.getOrderid());
        if (order1 != null) {
            if (order1.getStatus() == 0) {
                order1.setStatus((byte) 4);
                orderMapper.updateByPrimaryKeySelective(order1);
                redisCache.deleteObject(RedisCache.Order + order.getOrderid());
                redisCache.removeCacheSet(RedisCache.UnpaidOrders+order1.getUserid(),order1.getOrderid());
                redisCache.addCacheSet(RedisCache.FailedOrders+order1.getUserid(),order1.getOrderid());
            }
        }
    } @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "searchHistory-queue")
    public void createSearchHistory(SearchHistory searchHistory) {
        redisCache.setCacheObject(RedisCache.SearchHistory + searchHistory.getUserid(),searchHistory.getKeywords(), 10, TimeUnit.MINUTES);
        searchHistory.setSearchid(redisIdWoker.nextId("searchHistory"));
        LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
        searchHistory.setCreatetime(currentDateTime);
        searchHistoryMapper.insertSelective(searchHistory);

    }

}
