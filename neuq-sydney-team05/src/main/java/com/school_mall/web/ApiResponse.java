package com.school_mall.web;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code; // 0=ok
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.code = 0;
        r.message = "ok";
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> fail(String message) {
        ApiResponse<T> r = new ApiResponse<>();
        r.code = -1;
        r.message = message;
        return r;
    }
} 