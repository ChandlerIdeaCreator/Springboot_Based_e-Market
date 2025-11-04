package com.school_mall.repository;

import com.school_mall.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);
    int batchInsert(@Param("list") List<OrderItem> items);
} 