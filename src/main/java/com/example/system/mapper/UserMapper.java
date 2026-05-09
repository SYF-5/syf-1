package com.example.system.mapper;

import com.example.system.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO user (username, password, role, nickname, school_id) VALUES (#{username}, #{password}, #{role}, #{nickname}, #{schoolId})")
    void insert(User user);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE role = #{role}")
    List<User> findByRole(Integer role);

    @Select("SELECT * FROM user WHERE school_id = #{schoolId}")
    List<User> findBySchoolId(Long schoolId);

    @Select("SELECT * FROM user WHERE role = #{role} AND school_id = #{schoolId}")
    List<User> findByRoleAndSchoolId(Integer role, Long schoolId);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);

    @Update("UPDATE user SET avatar = #{avatar}, nickname = #{nickname} WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE user SET avatar = #{avatarUrl} WHERE id = #{userId}")
    int updateAvatar(Long userId, String avatarUrl);
}
