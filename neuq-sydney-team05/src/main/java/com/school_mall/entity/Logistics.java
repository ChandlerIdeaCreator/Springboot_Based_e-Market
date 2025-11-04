package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Logistics entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Logistics extends BaseEntity {
    /**
     * Order ID
     */
    private Long orderId;
    
    /**
     * Logistics tracking number
     */
    private String logisticsNo;
    
    /**
     * Express company name
     */
    private String expressName;
    
    /**
     * Logistics status: 0-pending, 1-shipped, 2-in-transit, 3-delivered
     */
    private Integer logisticsStatus;
} 