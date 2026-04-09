package com.example.system.controller;

import com.example.system.entity.Dish;
import com.example.system.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private DishService dishService;
    
    @GetMapping("/restaurant/{restaurantId}")
    public List<Dish> findByRestaurantId(@PathVariable Long restaurantId) {
        return dishService.findByRestaurantId(restaurantId);
    }
    
    @GetMapping("/{id}")
    public Dish findById(@PathVariable Long id) {
        return dishService.findById(id);
    }
}