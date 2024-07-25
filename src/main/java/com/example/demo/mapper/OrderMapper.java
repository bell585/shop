package com.example.demo.mapper;

import com.example.demo.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderid);

    int insert(Order row);

    int insertSelective(Order row);

    Order selectByPrimaryKey(Long orderid);

    int updateByPrimaryKeySelective(Order row);

    int updateByPrimaryKey(Order row);
    Order selectStatusByOrderId(Long orderId);
}