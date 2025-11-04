package com.school_mall.web.dto;

import lombok.Data;

public class AddressDtos {
    @Data
    public static class AddressReq {
        private Long id;
        private String receiver;
        private String phone;
        private String region;
        private String detailAddress;
        private Integer isDefault; // 0 default, 1 non-default
    }

    @Data
    public static class SetDefaultReq {
        private Long addressId;
    }
} 