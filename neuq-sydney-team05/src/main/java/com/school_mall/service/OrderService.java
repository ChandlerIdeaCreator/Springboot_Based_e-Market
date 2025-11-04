package com.school_mall.service;

import com.school_mall.entity.Order;
import com.school_mall.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Order service implementation
 */
@Service
public class OrderService extends BaseServiceImpl<Order, OrderMapper> {
    
    @Autowired
    private OrderMapper orderMapper;
    
    /**
     * Get order by order number
     */
    public Order getByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }
    
    /**
     * Get orders by user ID
     */
    public List<Order> getByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }
    
    /**
     * Get orders by status
     */
    public List<Order> getByStatus(Integer status) {
        return getAll().stream()
                .filter(order -> status.equals(order.getOrderStatus()))
                .toList();
    }
    
    /**
     * Update order status
     */
    public boolean updateOrderStatus(Long orderId, Integer status) {
        Order order = getById(orderId);
        if (order != null) {
            order.setOrderStatus(status);
            return update(order) != null;
        }
        return false;
    }
} 