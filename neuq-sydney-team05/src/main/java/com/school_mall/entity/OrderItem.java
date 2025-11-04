package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * Order item entity (one order can have multiple items)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {
    private Long orderId;
    private Long productId;
    private Long styleId;
    private String productName;
    private String productImg;
    private String styleName;
    private Integer productCount;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
} 