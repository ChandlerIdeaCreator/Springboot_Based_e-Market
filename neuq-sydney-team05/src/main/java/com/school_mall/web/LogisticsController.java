package com.school_mall.web;

import com.school_mall.entity.Logistics;
import com.school_mall.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LogisticsController {
    @Autowired
    private LogisticsService logisticsService;

    @GetMapping("/logistics/order/{orderId}")
    public ApiResponse<List<Logistics>> byOrder(@PathVariable Long orderId) {
        return ApiResponse.ok(logisticsService.getByOrderId(orderId));
    }

    @GetMapping("/logistics/{no}")
    public ApiResponse<Logistics> byNo(@PathVariable("no") String logisticsNo) {
        return ApiResponse.ok(logisticsService.getByLogisticsNo(logisticsNo));
    }
} 