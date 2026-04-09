package com.example.system.service.impl;

import com.example.system.common.Result;
import com.example.system.entity.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    public AuthServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result<String> register(User user) {
        // 检查用户名是否为空
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        // 检查密码是否为空
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        // 检查密码长度
        if (user.getPassword().length() < 6) {
            return Result.error("密码长度不能少于6位");
        }
        // 检查角色是否合法
        if (user.getRole() == null || (user.getRole() != 1 && user.getRole() != 2)) {
            return Result.error("角色必须是1或2");
        }
        // 检查用户名是否为手机号（admin除外）
        if (!"admin".equals(user.getUsername())) {
            String phoneRegex = "^1[3-9]\\d{9}$";
            if (!user.getUsername().matches(phoneRegex)) {
                return Result.error("用户名必须是手机号");
            }
        }
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }
        // 保存用户
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<Object> login(User user) {
        // 检查用户名是否为空
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        // 检查密码是否为空
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return Result.error("密码不能为空");
        }
        // 检查用户名是否为手机号（admin除外）
        if (!"admin".equals(user.getUsername())) {
            String phoneRegex = "^1[3-9]\\d{9}$";
            if (!user.getUsername().matches(phoneRegex)) {
                return Result.error("用户名必须是手机号");
            }
        }
        // 检查用户名和密码
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser == null) {
            return Result.error("用户名不存在");
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }
        // 检查身份是否匹配
        if (user.getRole() != null && !user.getRole().equals(existingUser.getRole())) {
            return Result.error("身份不匹配");
        }
        // 生成 token（这里简化处理，实际项目中应使用 JWT）
        String token = "token:" + existingUser.getId();
        Map<String, Object> data = new HashMap<>();
        data.put("id", existingUser.getId());
        data.put("username", existingUser.getUsername());
        data.put("role", existingUser.getRole());
        data.put("token", token);
        return Result.success(data);
    }
}