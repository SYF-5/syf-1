package com.example.system.service;

import com.example.system.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findByRole(Integer role);

    List<User> findBySchoolId(Long schoolId);

    List<User> findByRoleAndSchoolId(Integer role, Long schoolId);

    User findById(Long id);

    int update(User user);

    int updateAvatar(Long userId, String avatarUrl);
}
