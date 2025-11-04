package com.school_mall.repository;

import com.school_mall.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Order repository interface
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * Select order by ID
     */
    Order selectById(@Param("id") Long id);
    
    /**
     * Select all orders
     */
    List<Order> selectAll();
    
    /**
     * Select order by order number
     */
    Order selectByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * Select orders by user ID
     */
    List<Order> selectByUserId(@Param("userId") Long userId);
    
    /**
     * Insert order
     */
    int insert(Order order);
    
    /**
     * Insert order selectively
     */
    int insertSelective(Order order);
    
    /**
     * Update order by ID
     */
    int updateById(Order order);
    
    /**
     * Update order by ID selectively
     */
    int updateByIdSelective(Order order);
    
    /**
     * Delete order by ID
     */
    int deleteById(@Param("id") Long id);

    List<Order> selectByUserPaged(@Param("userId") Long userId,
                                  @Param("sortColumn") String sortColumn,
                                  @Param("sortOrder") String sortOrder,
                                  @Param("limit") int limit,
                                  @Param("offset") int offset);
} 