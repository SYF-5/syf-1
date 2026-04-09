package com.example.system.service;

import com.example.system.common.Result;
import com.example.system.entity.User;

public interface AuthService {
    Result<String> register(User user);
    Result<Object> login(User user);
}