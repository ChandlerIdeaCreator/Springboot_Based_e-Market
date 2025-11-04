package com.school_mall.service;

import com.school_mall.entity.LoginCode;
import com.school_mall.entity.User;
import com.school_mall.repository.LoginCodeMapper;
import com.school_mall.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class LoginCodeService {
    @Autowired
    private LoginCodeMapper loginCodeMapper;
    @Autowired
    private UserMapper userMapper;

    public String sendCode(String phone) {
        String code = String.format("%06d", new Random().nextInt(1000000));
        LoginCode lc = new LoginCode();
        lc.setPhone(phone);
        lc.setCode(code);
        lc.setExpireTime(LocalDateTime.now().plusMinutes(5));
        lc.setUsed(0);
        lc.setCreateTime(LocalDateTime.now());
        lc.setUpdateTime(LocalDateTime.now());
        loginCodeMapper.insertSelective(lc);
        return code;
    }

    public User loginOrRegister(String phone, String code) {
        LoginCode latest = loginCodeMapper.selectLatestByPhone(phone);
        if (latest == null || latest.getUsed() == 1 || latest.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("验证码无效或已过期");
        }
        if (!latest.getCode().equals(code)) {
            throw new RuntimeException("验证码错误");
        }
        loginCodeMapper.markUsed(latest.getId());
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            return userMapper.insertSelective(user) > 0 ? userMapper.selectByPhone(phone) : null;
        }
        return user;
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void cleanExpired() {
        loginCodeMapper.deleteExpired(LocalDateTime.now());
    }
} 