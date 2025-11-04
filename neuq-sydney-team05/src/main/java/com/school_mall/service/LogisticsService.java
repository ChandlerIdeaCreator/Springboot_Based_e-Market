package com.school_mall.service;

import com.school_mall.entity.Logistics;
import com.school_mall.repository.LogisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Logistics service for complex business operations
 */
@Service
public class LogisticsService extends BaseServiceImpl<Logistics, LogisticsMapper> {
    
    @Autowired
    private LogisticsMapper logisticsMapper;
    
    /**
     * Get logistics by tracking number
     */
    public Logistics getByLogisticsNo(String logisticsNo) {
        return logisticsMapper.selectByLogisticsNo(logisticsNo);
    }
    
    /**
     * Get logistics by order ID
     */
    public List<Logistics> getByOrderId(Long orderId) {
        return logisticsMapper.selectByOrderId(orderId);
    }
    
    /**
     * Get logistics by status
     */
    public List<Logistics> getByStatus(Integer status) {
        return logisticsMapper.selectByStatus(status);
    }
    
    /**
     * Get logistics by express company
     */
    public List<Logistics> getByExpressName(String expressName) {
        return logisticsMapper.selectByExpressName(expressName);
    }
    
    /**
     * Update logistics status by order ID
     */
    public boolean updateStatusByOrderId(Long orderId, Integer status) {
        return logisticsMapper.updateStatusByOrderId(orderId, status) > 0;
    }
    
    /**
     * Create logistics record for order
     */
    public Logistics createForOrder(Long orderId, String expressName, String logisticsNo) {
        Logistics logistics = new Logistics();
        logistics.setOrderId(orderId);
        logistics.setExpressName(expressName);
        logistics.setLogisticsNo(logisticsNo);
        logistics.setLogisticsStatus(0);
        return insert(logistics);
    }
    
    /**
     * Track shipment progress
     */
    public List<Logistics> trackShipment(String logisticsNo) {
        Logistics logistics = getByLogisticsNo(logisticsNo);
        if (logistics != null) {
            return getByOrderId(logistics.getOrderId());
        }
        return List.of();
    }
} 