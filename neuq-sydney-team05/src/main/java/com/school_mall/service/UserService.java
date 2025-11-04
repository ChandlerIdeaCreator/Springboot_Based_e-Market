package com.school_mall.service;

import com.school_mall.entity.User;
import com.school_mall.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service implementation
 */
@Service
public class UserService extends BaseServiceImpl<User, UserMapper> {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * Get user by phone number
     */
    public User getByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }
    
    /**
     * Check if phone number exists
     */
    public boolean existsByPhone(String phone) {
        return getByPhone(phone) != null;
    }
    
    /**
     * Register new user
     */
    public User register(User user) {
        if (existsByPhone(user.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }
        return insert(user);
    }
    
    /**
     * Login user by phone and password
     */
    public User login(String phone, String password) {
        User user = getByPhone(phone);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
} 