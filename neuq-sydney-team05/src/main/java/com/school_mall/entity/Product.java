package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * Product entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    /**
     * Shop type: 0-individual, 1-enterprise
     */
    private Integer shopType;
    
    /**
     * Product name
     */
    private String productName;
    
    /**
     * Original price
     */
    private BigDecimal originalPrice;
    
    /**
     * Discount price
     */
    private BigDecimal discountPrice;
    
    /**
     * Number of people who joined the group
     */
    private Integer joinedCount;
    
    /**
     * Product image URL
     */
    private String productImg;
    
    /**
     * Quality check status: 0-pending, 1-passed, 2-failed
     */
    private Integer qualityCheck;
} 