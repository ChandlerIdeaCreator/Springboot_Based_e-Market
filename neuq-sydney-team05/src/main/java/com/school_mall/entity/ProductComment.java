package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Product comment entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductComment extends BaseEntity {
    /**
     * Product ID
     */
    private Long productId;
    
    /**
     * User ID
     */
    private Long userId;
    
    /**
     * User nickname
     */
    private String nickname;
    
    /**
     * Comment content
     */
    private String commentContent;
    
    /**
     * Comment images (comma-separated URLs)
     */
    private String commentImg;
    
    /**
     * Is anonymous: 0-no, 1-yes
     */
    private Integer isAnonymous;
} 