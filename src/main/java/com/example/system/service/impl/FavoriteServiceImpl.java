package com.example.system.service.impl;

import com.example.system.entity.Favorite;
import com.example.system.mapper.FavoriteMapper;
import com.example.system.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public List<Favorite> findByUserId(Integer userId) {
        return favoriteMapper.findByUserId(userId);
    }

    @Override
    public List<Favorite> findByUserIdWithRestaurant(Integer userId) {
        return favoriteMapper.findByUserIdWithRestaurant(userId);
    }

    @Override
    public Favorite addFavorite(Favorite favorite) {
        // 先检查是否已经收藏
        if (existsByUserIdAndRestaurantId(favorite.getUserId(), favorite.getRestaurantId())) {
            // 如果已存在，返回已有的收藏记录
            return findByUserIdAndRestaurantId(favorite.getUserId(), favorite.getRestaurantId());
        }
        favoriteMapper.insert(favorite);
        return favorite;
    }

    @Override
    public void removeFavorite(Integer id) {
        favoriteMapper.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndRestaurantId(Integer userId, Integer restaurantId) {
        return favoriteMapper.countByUserIdAndRestaurantId(userId, restaurantId) > 0;
    }

    @Override
    public Favorite findByUserIdAndRestaurantId(Integer userId, Integer restaurantId) {
        return favoriteMapper.findByUserIdAndRestaurantId(userId, restaurantId);
    }
}