package com.example.system.service.impl;

import com.example.system.entity.Review;
import com.example.system.mapper.ReviewMapper;
import com.example.system.service.DishService;
import com.example.system.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final DishService dishService;

    public ReviewServiceImpl(ReviewMapper reviewMapper, DishService dishService) {
        this.reviewMapper = reviewMapper;
        this.dishService = dishService;
    }

    @Override
    public List<Review> findAllWithUserAndDish(int limit) {
        return reviewMapper.findAllWithUserAndDish(limit);
    }

    @Override
    public List<Review> findAllWithUserAndDishPaged(int offset, int limit) {
        return reviewMapper.findAllWithUserAndDishPaged(offset, limit);
    }

    @Override
    public List<Review> findByRestaurantId(int restaurantId, int limit) {
        return reviewMapper.findByRestaurantId(restaurantId, limit);
    }

    @Override
    public List<Review> findByRestaurantIdPaged(int restaurantId, int offset, int limit) {
        return reviewMapper.findByRestaurantIdPaged(restaurantId, offset, limit);
    }

    @Override
    public List<Review> findByDishId(int dishId) {
        return reviewMapper.findByDishId(dishId);
    }

    @Override
    public int countAll() {
        return reviewMapper.countAll();
    }

    @Override
    public int countByRestaurantId(int restaurantId) {
        return reviewMapper.countByRestaurantId(restaurantId);
    }

    @Override
    public int addReview(Review review) {
        int result = reviewMapper.insert(review);
        if (result > 0 && review.getDishId() != null) {
            Double averageScore = reviewMapper.calculateDishAverageScore(review.getDishId());
            if (averageScore == null) {
                averageScore = 0.0;
            }
            dishService.updateScore(review.getDishId(), averageScore);
        }
        return result;
    }

    @Override
    public List<Review> findAllReviewsForAdmin() {
        return reviewMapper.findAllReviewsForAdmin();
    }

    @Override
    public List<Review> findByStatus(int status) {
        return reviewMapper.findByStatus(status);
    }

    @Override
    public int updateStatus(Integer id, Integer status) {
        return reviewMapper.updateStatus(id, status);
    }

    @Override
    public int updateLikeCount(Integer id, Integer delta) {
        return reviewMapper.updateLikeCount(id, delta);
    }

    @Override
    public List<Review> findByDishIdWithStatus(Integer dishId) {
        return reviewMapper.findByDishIdWithStatus(dishId);
    }

    @Override
    public Double calculateDishAverageScore(Integer dishId) {
        return reviewMapper.calculateDishAverageScore(dishId);
    }
}
