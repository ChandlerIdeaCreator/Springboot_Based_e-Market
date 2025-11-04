package com.school_mall.security;

public class UserContext {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> PHONE = new ThreadLocal<>();

    public static void set(Long userId, String phone) {
        USER_ID.set(userId);
        PHONE.set(phone);
    }

    public static Long userId() {
        return USER_ID.get();
    }

    public static String phone() {
        return PHONE.get();
    }

    public static void clear() {
        USER_ID.remove();
        PHONE.remove();
    }
} 