package com.school_mall.service;

import com.school_mall.entity.ProductComment;
import com.school_mall.repository.ProductCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product comment service for complex business operations
 */
@Service
public class ProductCommentService extends BaseServiceImpl<ProductComment, ProductCommentMapper> {
    
    @Autowired
    private ProductCommentMapper commentMapper;
    
    /**
     * Get comments by product ID
     */
    public List<ProductComment> getByProductId(Long productId) {
        return commentMapper.selectByProductId(productId);
    }
    
    /**
     * Get comments by user ID
     */
    public List<ProductComment> getByUserId(Long userId) {
        return commentMapper.selectByUserId(userId);
    }
    
    /**
     * Get user comment for specific product
     */
    public ProductComment getUserCommentForProduct(Long productId, Long userId) {
        List<ProductComment> comments = commentMapper.selectByProductIdAndUserId(productId, userId);
        return comments.isEmpty() ? null : comments.get(0);
    }
    
    /**
     * Get anonymous comments for product
     */
    public List<ProductComment> getAnonymousComments(Long productId) {
        return commentMapper.selectAnonymousComments(productId);
    }
    
    /**
     * Get comments with images
     */
    public List<ProductComment> getCommentsWithImages(Long productId) {
        return commentMapper.selectWithImages(productId);
    }
    
    /**
     * Get comment count for product
     */
    public int getCommentCount(Long productId) {
        return commentMapper.countByProductId(productId);
    }
    
    /**
     * Get user comment count
     */
    public int getUserCommentCount(Long userId) {
        return commentMapper.countByUserId(userId);
    }
    
    /**
     * Check if user has commented on product
     */
    public boolean hasUserCommented(Long productId, Long userId) {
        return getUserCommentForProduct(productId, userId) != null;
    }
    
    /**
     * Add comment with business validation
     */
    public ProductComment addComment(ProductComment comment) {
        if (hasUserCommented(comment.getProductId(), comment.getUserId())) {
            throw new RuntimeException("User has already commented on this product");
        }
        
        if (comment.getIsAnonymous() == 1) {
            comment.setNickname("匿名用户");
        }
        
        return insert(comment);
    }
    
    /**
     * Delete all comments for a product
     */
    public boolean deleteByProductId(Long productId) {
        return commentMapper.deleteByProductId(productId) > 0;
    }
    
    /**
     * Delete all comments by a user
     */
    public boolean deleteByUserId(Long userId) {
        return commentMapper.deleteByUserId(userId) > 0;
    }
} 