package com.example.system.service;

import com.example.system.entity.School;

import java.util.List;

public interface SchoolService {

    List<School> findAll();

    School findById(Long id);

    int insert(School school);

    int update(School school);

    int deleteById(Long id);
}
