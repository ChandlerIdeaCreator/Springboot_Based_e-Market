package com.school_mall.web.dto;

import lombok.Data;

import java.time.LocalDate;

public class UserDtos {
    @Data
    public static class ProfileUpdateReq {
        private String nickname;
        private Integer gender;
        private LocalDate birthday;
    }
} 