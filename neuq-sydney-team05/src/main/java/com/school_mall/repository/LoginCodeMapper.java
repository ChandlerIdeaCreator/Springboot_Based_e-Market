package com.school_mall.repository;

import com.school_mall.entity.LoginCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LoginCodeMapper extends BaseMapper<LoginCode> {
    LoginCode selectLatestByPhone(@Param("phone") String phone);
    int markUsed(@Param("id") Long id);
    int deleteExpired(@Param("now") LocalDateTime now);
} 