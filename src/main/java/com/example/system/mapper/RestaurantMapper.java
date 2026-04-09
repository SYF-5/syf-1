package com.example.system.mapper;

import com.example.system.entity.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RestaurantMapper {
    @Select("SELECT id, name, image_url as imageUrl, description, avg_price as avgPrice, business_hours as businessHours, phone, address, category_code as categoryCode, created_at as createdAt, updated_at as updatedAt FROM restaurant")
    List<Restaurant> findAll();
    
    @Select("SELECT id, name, image_url as imageUrl, description, avg_price as avgPrice, business_hours as businessHours, phone, address, category_code as categoryCode, created_at as createdAt, updated_at as updatedAt FROM restaurant WHERE id = #{id}")
    Restaurant findById(Long id);
}