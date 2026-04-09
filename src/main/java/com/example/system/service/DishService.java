package com.example.system.service;

import com.example.system.entity.Dish;
import java.util.List;

public interface DishService {
    List<Dish> findByRestaurantId(Long restaurantId);
    Dish findById(Long id);
}