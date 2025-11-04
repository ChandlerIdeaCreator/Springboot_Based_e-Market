package com.school_mall.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

public class AuthDtos {
    @Data
    public static class SendCodeReq {
        @NotBlank(message = "手机号不能为空")
        private String phone;
    }

    @Data
    public static class LoginReq {
        @NotBlank(message = "手机号不能为空")
        private String phone;
        @NotBlank(message = "验证码不能为空")
        private String code;
    }
} 