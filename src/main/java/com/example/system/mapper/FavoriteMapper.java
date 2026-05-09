package com.example.system.mapper;

import com.example.system.entity.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("SELECT * FROM favorite WHERE user_id = #{userId}")
    List<Favorite> findByUserId(Integer userId);

    @Select("SELECT f.*, r.name, r.description, r.price_tag as priceTag, r.category_code as categoryCode " +
            "FROM favorite f " +
            "LEFT JOIN restaurant r ON f.restaurant_id = r.id " +
            "WHERE f.user_id = #{userId}")
    List<Favorite> findByUserIdWithRestaurant(Integer userId);

    @Insert("INSERT INTO favorite (user_id, restaurant_id) VALUES (#{userId}, #{restaurantId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Favorite favorite);

    @Delete("DELETE FROM favorite WHERE id = #{id}")
    void deleteById(Integer id);

    @Select("SELECT COUNT(*) FROM favorite WHERE user_id = #{userId} AND restaurant_id = #{restaurantId}")
    int countByUserIdAndRestaurantId(Integer userId, Integer restaurantId);

    @Select("SELECT * FROM favorite WHERE user_id = #{userId} AND restaurant_id = #{restaurantId}")
    Favorite findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
}
