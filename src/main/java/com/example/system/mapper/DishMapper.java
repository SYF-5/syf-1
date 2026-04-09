package com.example.system.mapper;

import com.example.system.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DishMapper {
    @Select("SELECT id, restaurant_id as restaurantId, name, description, price, image_url as imageUrl, created_at as createdAt, updated_at as updatedAt FROM dish WHERE restaurant_id = #{restaurantId}")
    List<Dish> findByRestaurantId(Long restaurantId);
    
    @Select("SELECT id, restaurant_id as restaurantId, name, description, price, image_url as imageUrl, created_at as createdAt, updated_at as updatedAt FROM dish WHERE id = #{id}")
    Dish findById(Long id);
}