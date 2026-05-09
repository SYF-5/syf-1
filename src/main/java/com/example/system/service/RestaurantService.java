package com.example.system.service;

import com.example.system.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> findByStatus(Integer status);

    List<Restaurant> findAll();

    Restaurant findById(Long id);

    List<Restaurant> findByMerchantId(Long merchantId);

    List<Restaurant> findByCategoryCode(Integer categoryCode);

    List<Restaurant> findByStatusAndCategoryCode(Integer status, Integer categoryCode);

    int updateStatus(Long id, Integer status);

    int insert(Restaurant restaurant);

    int updateImage(Long id, String imageUrl);

    void updateTotalScore(Long id, Double totalScore);

    Double calculateRestaurantAverageScore(Long restaurantId);
}
