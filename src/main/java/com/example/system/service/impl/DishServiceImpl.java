package com.example.system.service.impl;

import com.example.system.entity.Dish;
import com.example.system.mapper.DishMapper;
import com.example.system.service.DishService;
import com.example.system.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final RestaurantService restaurantService;

    public DishServiceImpl(DishMapper dishMapper, RestaurantService restaurantService) {
        this.dishMapper = dishMapper;
        this.restaurantService = restaurantService;
    }

    @Override
    public List<Dish> findByStatus(Integer status) {
        return dishMapper.findByStatus(status);
    }

    @Override
    public List<Dish> findAll() {
        return dishMapper.findAll();
    }

    @Override
    public List<Dish> findByRestaurantId(Integer restaurantId) {
        return dishMapper.findByRestaurantId(restaurantId);
    }

    @Override
    public Dish findById(Integer id) {
        return dishMapper.findById(id);
    }

    @Override
    public int updateStatus(Integer id, Integer status) {
        return dishMapper.updateStatus(id, status);
    }

    @Override
    public Dish save(Dish dish) {
        if (dish.getId() == null) {
            dishMapper.insert(dish);
        } else {
            dishMapper.updateById(dish);
        }
        return dish;
    }

    @Override
    public int updateImage(Integer id, String imageUrl) {
        return dishMapper.updateImage(id, imageUrl);
    }

    @Override
    public void updateScore(Integer id, Double score) {
        dishMapper.updateScore(id, score);
        
        Integer restaurantId = dishMapper.getRestaurantIdByDishId(id);
        if (restaurantId != null) {
            Double restaurantAvgScore = restaurantService.calculateRestaurantAverageScore(restaurantId.longValue());
            if (restaurantAvgScore == null) {
                restaurantAvgScore = 0.0;
            }
            restaurantService.updateTotalScore(restaurantId.longValue(), restaurantAvgScore);
        }
    }
}
