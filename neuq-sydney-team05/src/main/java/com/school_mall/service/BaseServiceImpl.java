package com.school_mall.service;

import com.school_mall.entity.BaseEntity;
import com.school_mall.repository.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Base service implementation providing common CRUD operations
 * @param <T> Entity type extending BaseEntity
 * @param <M> Mapper type extending BaseMapper
 */
@Service
public abstract class BaseServiceImpl<T extends BaseEntity, M extends BaseMapper<T>> implements BaseService<T> {
    
    @Autowired
    protected M mapper;
    
    @Override
    public T getById(Long id) {
        return mapper.selectById(id);
    }
    
    @Override
    public List<T> getAll() {
        return mapper.selectAll();
    }
    
    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }
    
    @Override
    public T insert(T entity) {
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        mapper.insertSelective(entity);
        return entity;
    }
    
    @Override
    public T update(T entity) {
        entity.setUpdateTime(LocalDateTime.now());
        mapper.updateByIdSelective(entity);
        return entity;
    }
    
    @Override
    public boolean deleteById(Long id) {
        return mapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean existsById(Long id) {
        return getById(id) != null;
    }
    
    @Override
    public long count() {
        return getAll().size();
    }
} 