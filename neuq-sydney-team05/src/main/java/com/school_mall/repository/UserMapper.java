package com.school_mall.repository;

import com.school_mall.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User repository interface
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * Select user by ID
     */
    User selectById(@Param("id") Long id);
    
    /**
     * Select all users
     */
    List<User> selectAll();
    
    /**
     * Select user by phone
     */
    User selectByPhone(@Param("phone") String phone);
    
    /**
     * Insert user
     */
    int insert(User user);
    
    /**
     * Insert user selectively
     */
    int insertSelective(User user);
    
    /**
     * Update user by ID
     */
    int updateById(User user);
    
    /**
     * Update user by ID selectively
     */
    int updateByIdSelective(User user);
    
    /**
     * Delete user by ID
     */
    int deleteById(@Param("id") Long id);
} 