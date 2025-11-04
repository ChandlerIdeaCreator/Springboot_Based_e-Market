package com.school_mall.repository;

import com.school_mall.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Product repository interface
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    
    /**
     * Select product by ID
     */
    Product selectById(@Param("id") Long id);
    
    /**
     * Select all products
     */
    List<Product> selectAll();
    
    /**
     * Insert product
     */
    int insert(Product product);
    
    /**
     * Insert product selectively
     */
    int insertSelective(Product product);
    
    /**
     * Update product by ID
     */
    int updateById(Product product);
    
    /**
     * Update product by ID selectively
     */
    int updateByIdSelective(Product product);
    
    /**
     * Delete product by ID
     */
    int deleteById(@Param("id") Long id);

    List<Product> selectPaged(@Param("name") String name,
                              @Param("sortColumn") String sortColumn,
                              @Param("sortOrder") String sortOrder,
                              @Param("limit") int limit,
                              @Param("offset") int offset);
} 