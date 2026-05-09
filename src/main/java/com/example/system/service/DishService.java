package com.example.system.service;

import com.example.system.entity.Dish;

import java.util.List;

public interface DishService {

    List<Dish> findByStatus(Integer status);

    List<Dish> findAll();

    List<Dish> findByRestaurantId(Integer restaurantId);

    Dish findById(Integer id);

    int updateStatus(Integer id, Integer status);

    Dish save(Dish dish);

    int updateImage(Integer id, String imageUrl);

    void updateScore(Integer id, Double score);
}
