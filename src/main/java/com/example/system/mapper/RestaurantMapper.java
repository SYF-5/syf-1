package com.example.system.mapper;

import com.example.system.entity.Restaurant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    @Select("SELECT *, image_url as imageUrl FROM restaurant WHERE status = #{status}")
    List<Restaurant> findByStatus(Integer status);

    @Select("SELECT *, image_url as imageUrl FROM restaurant")
    List<Restaurant> findAll();

    @Select("SELECT *, image_url as imageUrl FROM restaurant WHERE id = #{id}")
    Restaurant findById(Long id);

    @Select("SELECT *, image_url as imageUrl FROM restaurant WHERE merchant_id = #{merchantId}")
    List<Restaurant> findByMerchantId(Long merchantId);

    @Select("SELECT *, image_url as imageUrl FROM restaurant WHERE category_code = #{categoryCode}")
    List<Restaurant> findByCategoryCode(Integer categoryCode);

    @Select("SELECT *, image_url as imageUrl FROM restaurant WHERE status = #{status} AND category_code = #{categoryCode}")
    List<Restaurant> findByStatusAndCategoryCode(Integer status, Integer categoryCode);

    @Update("UPDATE restaurant SET status = #{status} WHERE id = #{id}")
    int updateStatus(Long id, Integer status);

    @Insert("INSERT INTO restaurant (name, description, address, business_hours, phone, status, merchant_id, price_tag, category_code, longitude, latitude, created_at) " +
            "VALUES (#{name}, #{description}, #{address}, #{businessHours}, #{phone}, #{status}, #{merchantId}, #{priceTag}, #{categoryCode}, #{longitude}, #{latitude}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Restaurant restaurant);

    @Update("UPDATE restaurant SET image_url = #{imageUrl}, updated_at = NOW() WHERE id = #{id}")
    int updateImage(Long id, String imageUrl);

    @Update("UPDATE restaurant SET total_score = #{totalScore} WHERE id = #{id}")
    int updateTotalScore(Long id, Double totalScore);

    @Select("SELECT COALESCE(AVG(d.score), 0) FROM dish d WHERE d.restaurant_id = #{restaurantId} AND d.status = 1")
    Double calculateRestaurantAverageScore(Long restaurantId);
}
