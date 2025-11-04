package com.school_mall.repository;

import com.school_mall.entity.ProductStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Product style repository interface
 */
@Mapper
public interface ProductStyleMapper extends BaseMapper<ProductStyle> {
    
    /**
     * Select style by ID
     */
    ProductStyle selectById(@Param("id") Long id);
    
    /**
     * Select all styles
     */
    List<ProductStyle> selectAll();
    
    /**
     * Select styles by product ID
     */
    List<ProductStyle> selectByProductId(@Param("productId") Long productId);
    
    /**
     * Search by style name (LIKE)
     */
    List<ProductStyle> selectByStyleName(@Param("styleName") String styleName);
    
    /**
     * Select by product and style name
     */
    ProductStyle selectByProductIdAndStyleName(@Param("productId") Long productId,
                                               @Param("styleName") String styleName);
    
    /**
     * Count styles for product
     */
    int countByProductId(@Param("productId") Long productId);
    
    /**
     * Batch insert styles
     */
    int batchInsert(@Param("list") List<ProductStyle> styles);
    
    /**
     * Insert style
     */
    int insert(ProductStyle style);
    
    /**
     * Insert style selectively
     */
    int insertSelective(ProductStyle style);
    
    /**
     * Update style by ID
     */
    int updateById(ProductStyle style);
    
    /**
     * Update style by ID selectively
     */
    int updateByIdSelective(ProductStyle style);
    
    /**
     * Delete style by ID
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * Delete all styles by product ID
     */
    int deleteByProductId(@Param("productId") Long productId);
    
    /**
     * Delete by productId and styleName
     */
    int deleteByProductIdAndStyleName(@Param("productId") Long productId,
                                      @Param("styleName") String styleName);
} 