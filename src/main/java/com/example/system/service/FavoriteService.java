package com.example.system.service;

import com.example.system.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> findByUserId(Integer userId);
    List<Favorite> findByUserIdWithRestaurant(Integer userId);
    Favorite addFavorite(Favorite favorite);
    void removeFavorite(Integer id);
    boolean existsByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
    Favorite findByUserIdAndRestaurantId(Integer userId, Integer restaurantId);
}