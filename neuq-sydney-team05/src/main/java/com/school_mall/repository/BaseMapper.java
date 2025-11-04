package com.school_mall.repository;

import com.school_mall.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Base mapper interface providing common CRUD operations
 * @param <T> Entity type extending BaseEntity
 */
public interface BaseMapper<T extends BaseEntity> {
    
    /**
     * Select entity by ID
     */
    T selectById(@Param("id") Long id);
    
    /**
     * Select all entities
     */
    List<T> selectAll();
    
    /**
     * Insert entity
     */
    int insert(T entity);
    
    /**
     * Insert entity selectively
     */
    int insertSelective(T entity);
    
    /**
     * Update entity by ID
     */
    int updateById(T entity);
    
    /**
     * Update entity by ID selectively
     */
    int updateByIdSelective(T entity);
    
    /**
     * Delete entity by ID
     */
    int deleteById(@Param("id") Long id);
} 