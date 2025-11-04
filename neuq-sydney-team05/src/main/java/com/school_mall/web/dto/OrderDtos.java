package com.school_mall.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtos {
    @Data
    public static class OrderItemReq {
        private Long productId;
        private Long styleId; // optional
        private Integer count;
    }

    @Data
    public static class CreateOrderReq {
        private Long addressId; // or provide snapshot fields directly
        private List<OrderItemReq> items;
    }

    @Data
    public static class PayReq {
        private Long orderId;
    }
} 