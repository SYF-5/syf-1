package com.example.system.service.impl;

import com.example.system.dto.UserDetailDTO;
import com.example.system.entity.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.UserDetailService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private final UserMapper userMapper;

    public UserDetailServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailDTO getUserDetail(Long userId) {
        List<User> users = userMapper.findAll();
        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);

        return new UserDetailDTO(user, new ArrayList<>(), new ArrayList<>());
    }
}
