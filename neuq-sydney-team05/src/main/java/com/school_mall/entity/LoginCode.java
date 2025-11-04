package com.school_mall.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginCode extends BaseEntity {
    private String phone;
    private String code;
    private LocalDateTime expireTime;
    private Integer used; // 0: unused, 1: used
} 