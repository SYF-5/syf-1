package com.example.system.mapper;

import com.example.system.entity.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Insert("INSERT INTO review (user_id, dish_id, content, images, taste_score, environment_score, service_score, like_count, status, created_at) " +
            "VALUES (#{userId}, #{dishId}, #{content}, #{images}, #{tasteScore}, #{environmentScore}, #{serviceScore}, #{likeCount}, #{status}, NOW())")
    int insert(Review review);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.status = 0 ORDER BY r.created_at DESC LIMIT #{limit}")
    List<Review> findAllWithUserAndDish(int limit);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.status = 0 ORDER BY r.created_at DESC LIMIT #{offset}, #{limit}")
    List<Review> findAllWithUserAndDishPaged(int offset, int limit);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE d.restaurant_id = #{restaurantId} AND r.status = 0 ORDER BY r.created_at DESC LIMIT #{limit}")
    List<Review> findByRestaurantId(int restaurantId, int limit);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE d.restaurant_id = #{restaurantId} AND r.status = 0 ORDER BY r.created_at DESC LIMIT #{offset}, #{limit}")
    List<Review> findByRestaurantIdPaged(int restaurantId, int offset, int limit);

    @Select("SELECT COUNT(*) FROM review r LEFT JOIN dish d ON r.dish_id = d.id WHERE d.restaurant_id = #{restaurantId} AND r.status = 0")
    int countByRestaurantId(int restaurantId);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.dish_id = #{dishId} AND r.status = 0 ORDER BY r.created_at DESC")
    List<Review> findByDishId(int dishId);

    @Select("SELECT COUNT(*) FROM review WHERE status = 0")
    int countAll();

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.status IN (0, 1) ORDER BY r.created_at DESC")
    List<Review> findAllReviewsForAdmin();

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.status = #{status} ORDER BY r.created_at DESC")
    List<Review> findByStatus(int status);

    @Update("UPDATE review SET status = #{status} WHERE id = #{id}")
    int updateStatus(Integer id, Integer status);

    @Update("UPDATE review SET like_count = like_count + #{delta} WHERE id = #{id}")
    int updateLikeCount(Integer id, Integer delta);

    @Select("SELECT r.*, u.nickname, u.avatar, d.name as dishName " +
            "FROM review r " +
            "LEFT JOIN user u ON r.user_id = u.id " +
            "LEFT JOIN dish d ON r.dish_id = d.id " +
            "WHERE r.dish_id = #{dishId} AND r.status IN (0, 1) ORDER BY r.created_at DESC")
    List<Review> findByDishIdWithStatus(Integer dishId);

    @Select("SELECT AVG((taste_score + environment_score + service_score) / 3.0) FROM review WHERE dish_id = #{dishId} AND status IN (0, 1)")
    Double calculateDishAverageScore(Integer dishId);
}
