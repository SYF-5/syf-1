package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.School;
import com.example.system.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public Result<List<School>> getAllSchools() {
        List<School> schools = schoolService.findAll();
        return Result.success(schools);
    }

    @GetMapping("/{id}")
    public Result<School> getSchoolById(@PathVariable Long id) {
        School school = schoolService.findById(id);
        if (school != null) {
            return Result.success(school);
        }
        return Result.error("学校不存在");
    }

    @PostMapping
    public Result<String> createSchool(@RequestBody School school) {
        int result = schoolService.insert(school);
        if (result > 0) {
            return Result.success("添加成功");
        }
        return Result.error("添加失败");
    }

    @PutMapping("/{id}")
    public Result<String> updateSchool(@PathVariable Long id, @RequestBody School school) {
        school.setId(id);
        int result = schoolService.update(school);
        if (result > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteSchool(@PathVariable Long id) {
        int result = schoolService.deleteById(id);
        if (result > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
