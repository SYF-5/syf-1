package com.example.system.mapper;

import com.example.system.entity.School;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolMapper {

    @Select("SELECT id, name, address, longitude, latitude, create_time AS createTime FROM school")
    List<School> findAll();

    @Select("SELECT id, name, address, longitude, latitude, create_time AS createTime FROM school WHERE id = #{id}")
    School findById(Long id);

    @Insert("INSERT INTO school(name, address, longitude, latitude, create_time) VALUES(#{name}, #{address}, #{longitude}, #{latitude}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(School school);

    @Update("UPDATE school SET name = #{name}, address = #{address}, longitude = #{longitude}, latitude = #{latitude} WHERE id = #{id}")
    int update(School school);

    @Delete("DELETE FROM school WHERE id = #{id}")
    int deleteById(Long id);
}
