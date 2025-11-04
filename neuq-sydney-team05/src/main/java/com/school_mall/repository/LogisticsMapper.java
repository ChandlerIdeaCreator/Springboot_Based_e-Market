package com.school_mall.repository;

import com.school_mall.entity.Logistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Logistics repository interface
 */
@Mapper
public interface LogisticsMapper extends BaseMapper<Logistics> {
    
    /**
     * Select logistics by ID
     */
    Logistics selectById(@Param("id") Long id);
    
    /**
     * Select all logistics
     */
    List<Logistics> selectAll();
    
    /**
     * Select logistics by tracking number
     */
    Logistics selectByLogisticsNo(@Param("logisticsNo") String logisticsNo);
    
    /**
     * Select logistics by order ID
     */
    List<Logistics> selectByOrderId(@Param("orderId") Long orderId);
    
    /**
     * Select by status
     */
    List<Logistics> selectByStatus(@Param("status") Integer status);
    
    /**
     * Select by express company name
     */
    List<Logistics> selectByExpressName(@Param("expressName") String expressName);
    
    /**
     * Insert logistics
     */
    int insert(Logistics logistics);
    
    /**
     * Insert logistics selectively
     */
    int insertSelective(Logistics logistics);
    
    /**
     * Update logistics by ID
     */
    int updateById(Logistics logistics);
    
    /**
     * Update logistics by ID selectively
     */
    int updateByIdSelective(Logistics logistics);
    
    /**
     * Update logistics status by order ID
     */
    int updateStatusByOrderId(@Param("orderId") Long orderId, @Param("status") Integer status);
    
    /**
     * Delete logistics by ID
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * Delete logistics by order ID
     */
    int deleteByOrderId(@Param("orderId") Long orderId);
} 