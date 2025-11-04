package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order entity (master data, address snapshot, status)
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    /**
     * Order number (unique)
     */
    private String orderNo;
    
    /**
     * User ID
     */
    private Long userId;
    
    /**
     * Order status: 0-pending, 1-paid, 2-cancelled, 3-completed
     */
    private Integer orderStatus;
    
    /**
     * Total amount (order-level)
     */
    private BigDecimal totalAmount;
    
    /**
     * Address snapshot
     */
    private String receiver;
    private String receiverPhone;
    private String region;
    private String detailAddress;
    
    /**
     * Payment time
     */
    private LocalDateTime payTime;
} 