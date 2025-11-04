package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * User entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /**
     * Phone number (unique identifier)
     */
    private String phone;
    
    /**
     * User nickname
     */
    private String nickname;
    
    /**
     * Gender: 0-female, 1-male, 2-other
     */
    private Integer gender;
    
    /**
     * Birthday
     */
    private LocalDate birthday;
    
    /**
     * Password (encrypted)
     */
    private String password;
} 