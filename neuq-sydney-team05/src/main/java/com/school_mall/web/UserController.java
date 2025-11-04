package com.school_mall.web;

import com.school_mall.entity.User;
import com.school_mall.entity.UserAddress;
import com.school_mall.service.UserAddressService;
import com.school_mall.service.UserService;
import com.school_mall.web.dto.AddressDtos;
import com.school_mall.web.dto.UserDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAddressService addressService;

    // For demo purposes, we accept userId as param; real app uses auth context
    @GetMapping("/users/{userId}")
    public ApiResponse<User> getProfile(@PathVariable Long userId) {
        return ApiResponse.ok(userService.getById(userId));
    }

    @PutMapping("/users/{userId}")
    public ApiResponse<User> updateProfile(@PathVariable Long userId, @RequestBody UserDtos.ProfileUpdateReq req) {
        User u = userService.getById(userId);
        if (u == null) return ApiResponse.fail("用户不存在哦");
        u.setNickname(req.getNickname());
        u.setGender(req.getGender());
        u.setBirthday(req.getBirthday());
        return ApiResponse.ok(userService.update(u));
    }

    @GetMapping("/users/{userId}/addresses")
    public ApiResponse<List<UserAddress>> listAddresses(@PathVariable Long userId) {
        return ApiResponse.ok(addressService.getByUserId(userId));
    }

    @PostMapping("/users/{userId}/addresses")
    public ApiResponse<UserAddress> addAddress(@PathVariable Long userId, @RequestBody AddressDtos.AddressReq req) {
        UserAddress addr = new UserAddress();
        addr.setUserId(userId);
        addr.setReceiver(req.getReceiver());
        addr.setPhone(req.getPhone());
        addr.setRegion(req.getRegion());
        addr.setDetailAddress(req.getDetailAddress());
        addr.setIsDefault(req.getIsDefault());
        return ApiResponse.ok(addressService.addAddress(addr));
    }

    @PutMapping("/users/{userId}/addresses/{id}")
    public ApiResponse<UserAddress> updateAddress(@PathVariable Long userId, @PathVariable Long id, @RequestBody AddressDtos.AddressReq req) {
        UserAddress addr = addressService.getById(id);
        if (addr == null || !addr.getUserId().equals(userId)) return ApiResponse.fail("地址不存在或无权限哦");
        addr.setReceiver(req.getReceiver());
        addr.setPhone(req.getPhone());
        addr.setRegion(req.getRegion());
        addr.setDetailAddress(req.getDetailAddress());
        addr.setIsDefault(req.getIsDefault());
        return ApiResponse.ok(addressService.updateAddress(addr));
    }

    @DeleteMapping("/users/{userId}/addresses/{id}")
    public ApiResponse<Boolean> deleteAddress(@PathVariable Long userId, @PathVariable Long id) {
        return ApiResponse.ok(addressService.deleteAddress(id, userId));
    }

    @PutMapping("/users/{userId}/addresses/{id}/default")
    public ApiResponse<Boolean> setDefault(@PathVariable Long userId, @PathVariable Long id) {
        return ApiResponse.ok(addressService.setAsDefault(userId, id));
    }
} 
