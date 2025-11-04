package com.school_mall.service;

import com.school_mall.entity.BaseEntity;
import java.util.List;

/**
 * Base service interface providing common CRUD operations
 * @param <T> Entity type extending BaseEntity
 */
public interface BaseService<T extends BaseEntity> {
    
    /**
     * Get entity by ID
     */
    T getById(Long id);
    
    /**
     * Get all entities
     */
    List<T> getAll();
    
    /**
     * Save entity (insert or update)
     */
    T save(T entity);
    
    /**
     * Insert entity
     */
    T insert(T entity);
    
    /**
     * Update entity
     */
    T update(T entity);
    
    /**
     * Delete entity by ID
     */
    boolean deleteById(Long id);
    
    /**
     * Check if entity exists by ID
     */
    boolean existsById(Long id);
    
    /**
     * Get count of entities
     */
    long count();
} 