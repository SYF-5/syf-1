package com.example.system.service.impl;

import com.example.system.entity.Restaurant;
import com.example.system.mapper.RestaurantMapper;
import com.example.system.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantMapper restaurantMapper;
    
    @Override
    public List<Restaurant> findAll() {
        return restaurantMapper.findAll();
    }
    
    @Override
    public Restaurant findById(Long id) {
        return restaurantMapper.findById(id);
    }
}