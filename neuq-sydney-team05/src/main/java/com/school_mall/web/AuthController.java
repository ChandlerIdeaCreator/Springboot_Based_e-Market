package com.school_mall.web;

import com.school_mall.entity.User;
import com.school_mall.security.JwtUtil;
import com.school_mall.web.dto.AuthDtos;
import com.school_mall.service.LoginCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private LoginCodeService loginCodeService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/send-code")
    public ApiResponse<String> sendCode(@Valid @RequestBody AuthDtos.SendCodeReq req) {
        String code = loginCodeService.sendCode(req.getPhone());
        return ApiResponse.ok(code);
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody AuthDtos.LoginReq req) {
        User user = loginCodeService.loginOrRegister(req.getPhone(), req.getCode());
        String token = jwtUtil.createToken(user.getId(), user.getPhone());
        Map<String, Object> r = new HashMap<>();
        r.put("token", token);
        r.put("user", user);
        return ApiResponse.ok(r);
    }
} 