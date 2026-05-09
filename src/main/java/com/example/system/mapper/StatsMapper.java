package com.example.system.mapper;

import org.apache.ibatis.annotations.Select;

public interface StatsMapper {

    @Select("SELECT COUNT(*) FROM user")
    Long countUsers();

    @Select("SELECT COUNT(*) FROM restaurant WHERE status = #{status}")
    Long countStoresByStatus(Integer status);

    @Select("SELECT COUNT(*) FROM review")
    Long countReviews();
}