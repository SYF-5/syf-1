package com.example.system.service;

import com.example.system.entity.Restaurant;
import java.util.List;

public interface RestaurantService {
    List<Restaurant> findAll();
    Restaurant findById(Long id);
}