package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Product style entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductStyle extends BaseEntity {
    /**
     * Product ID
     */
    private Long productId;
    
    /**
     * Style name (e.g., color, size)
     */
    private String styleName;
} 