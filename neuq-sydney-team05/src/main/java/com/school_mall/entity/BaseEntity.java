package com.school_mall.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Base entity class containing common fields
 */
@Data
public abstract class BaseEntity {
    /**
     * Primary key ID
     */
    private Long id;
    
    /**
     * Creation time
     */
    private LocalDateTime createTime;
    
    /**
     * Last update time
     */
    private LocalDateTime updateTime;
} 