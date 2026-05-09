package com.example.system.service.impl;

import com.example.system.entity.School;
import com.example.system.mapper.SchoolMapper;
import com.example.system.service.SchoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    public List<School> findAll() {
        return schoolMapper.findAll();
    }

    @Override
    public School findById(Long id) {
        return schoolMapper.findById(id);
    }

    @Override
    public int insert(School school) {
        return schoolMapper.insert(school);
    }

    @Override
    public int update(School school) {
        return schoolMapper.update(school);
    }

    @Override
    public int deleteById(Long id) {
        return schoolMapper.deleteById(id);
    }
}
