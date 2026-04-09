package com.example.system.service.impl;

import com.example.system.entity.Dish;
import com.example.system.mapper.DishMapper;
import com.example.system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    
    @Override
    public List<Dish> findByRestaurantId(Long restaurantId) {
        return dishMapper.findByRestaurantId(restaurantId);
    }
    
    @Override
    public Dish findById(Long id) {
        return dishMapper.findById(id);
    }
}