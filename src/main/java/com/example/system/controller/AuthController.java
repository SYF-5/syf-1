package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.User;
import com.example.system.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody User user) {
        return authService.login(user);
    }
}