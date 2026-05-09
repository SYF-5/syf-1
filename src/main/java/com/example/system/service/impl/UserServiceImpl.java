package com.example.system.service.impl;

import com.example.system.entity.User;
import com.example.system.mapper.UserMapper;
import com.example.system.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public List<User> findByRole(Integer role) {
        return userMapper.findByRole(role);
    }

    @Override
    public List<User> findBySchoolId(Long schoolId) {
        return userMapper.findBySchoolId(schoolId);
    }

    @Override
    public List<User> findByRoleAndSchoolId(Integer role, Long schoolId) {
        return userMapper.findByRoleAndSchoolId(role, schoolId);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int updateAvatar(Long userId, String avatarUrl) {
        return userMapper.updateAvatar(userId, avatarUrl);
    }
}
