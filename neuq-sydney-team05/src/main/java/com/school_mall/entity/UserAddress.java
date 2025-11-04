package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User address entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddress extends BaseEntity {
    /**
     * User ID
     */
    private Long userId;
    
    /**
     * Receiver name
     */
    private String receiver;
    
    /**
     * Receiver phone
     */
    private String phone;
    
    /**
     * Region (province, city, district)
     */
    private String region;
    
    /**
     * Detailed address
     */
    private String detailAddress;
    
    /**
     * Is default address: 0-no, 1-yes
     */
    private Integer isDefault;
} 