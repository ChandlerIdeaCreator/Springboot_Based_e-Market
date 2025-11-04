package com.school_mall.repository;

import com.school_mall.entity.ProductComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Product comment repository interface
 */
@Mapper
public interface ProductCommentMapper extends BaseMapper<ProductComment> {
    
    /**
     * Select comment by ID
     */
    ProductComment selectById(@Param("id") Long id);
    
    /**
     * Select all comments
     */
    List<ProductComment> selectAll();
    
    /**
     * Select comments by product ID
     */
    List<ProductComment> selectByProductId(@Param("productId") Long productId);
    
    /**
     * Select comments by user ID
     */
    List<ProductComment> selectByUserId(@Param("userId") Long userId);
    
    /**
     * Select by product and user
     */
    List<ProductComment> selectByProductIdAndUserId(@Param("productId") Long productId,
                                                    @Param("userId") Long userId);
    
    /**
     * Select anonymous comments
     */
    List<ProductComment> selectAnonymousComments(@Param("productId") Long productId);
    
    /**
     * Select comments with images
     */
    List<ProductComment> selectWithImages(@Param("productId") Long productId);
    
    /**
     * Count by product
     */
    int countByProductId(@Param("productId") Long productId);
    
    /**
     * Count by user
     */
    int countByUserId(@Param("userId") Long userId);
    
    /**
     * Insert comment
     */
    int insert(ProductComment comment);
    
    /**
     * Insert comment selectively
     */
    int insertSelective(ProductComment comment);
    
    /**
     * Update comment by ID
     */
    int updateById(ProductComment comment);
    
    /**
     * Update comment by ID selectively
     */
    int updateByIdSelective(ProductComment comment);
    
    /**
     * Delete comment by ID
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * Delete comments by product ID
     */
    int deleteByProductId(@Param("productId") Long productId);
    
    /**
     * Delete comments by user ID
     */
    int deleteByUserId(@Param("userId") Long userId);
} 