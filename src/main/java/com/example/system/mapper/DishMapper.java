package com.example.system.mapper;

import com.example.system.entity.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("SELECT d.*, d.image_url as imageUrl, " +
            "COALESCE((SELECT AVG((r.taste_score + r.environment_score + r.service_score) / 3.0) " +
            "FROM review r WHERE r.dish_id = d.id AND r.status IN (0, 1)), 0) as score " +
            "FROM dish d WHERE d.status = #{status}")
    List<Dish> findByStatus(Integer status);

    @Select("SELECT d.*, d.image_url as imageUrl, " +
            "COALESCE((SELECT AVG((r.taste_score + r.environment_score + r.service_score) / 3.0) " +
            "FROM review r WHERE r.dish_id = d.id AND r.status IN (0, 1)), 0) as score " +
            "FROM dish d")
    List<Dish> findAll();

    @Select("SELECT d.*, d.image_url as imageUrl, " +
            "COALESCE((SELECT AVG((r.taste_score + r.environment_score + r.service_score) / 3.0) " +
            "FROM review r WHERE r.dish_id = d.id AND r.status IN (0, 1)), 0) as score " +
            "FROM dish d WHERE d.restaurant_id = #{restaurantId}")
    List<Dish> findByRestaurantId(Integer restaurantId);

    @Select("SELECT d.*, d.image_url as imageUrl, " +
            "COALESCE((SELECT AVG((r.taste_score + r.environment_score + r.service_score) / 3.0) " +
            "FROM review r WHERE r.dish_id = d.id AND r.status IN (0, 1)), 0) as score " +
            "FROM dish d WHERE d.id = #{id}")
    Dish findById(Integer id);

    @Update("UPDATE dish SET status = #{status} WHERE id = #{id}")
    int updateStatus(Integer id, Integer status);

    @Update("UPDATE dish SET score = #{score} WHERE id = #{id}")
    int updateScore(Integer id, Double score);

    @Select("SELECT restaurant_id FROM dish WHERE id = #{id}")
    Integer getRestaurantIdByDishId(Integer id);

    @Insert("INSERT INTO dish (restaurant_id, name, description, price, image_url, status, created_at, updated_at) " +
            "VALUES (#{restaurantId}, #{name}, #{description}, #{price}, #{imageUrl}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Dish dish);

    @Update("UPDATE dish SET restaurant_id = #{restaurantId}, name = #{name}, description = #{description}, " +
            "price = #{price}, image_url = #{imageUrl}, status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateById(Dish dish);

    @Update("UPDATE dish SET image_url = #{imageUrl}, updated_at = NOW() WHERE id = #{id}")
    int updateImage(Integer id, String imageUrl);
}
