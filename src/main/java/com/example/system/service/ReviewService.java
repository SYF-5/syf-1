package com.example.system.service;

import com.example.system.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> findAllWithUserAndDish(int limit);

    List<Review> findAllWithUserAndDishPaged(int offset, int limit);

    List<Review> findByRestaurantId(int restaurantId, int limit);

    List<Review> findByRestaurantIdPaged(int restaurantId, int offset, int limit);

    List<Review> findByDishId(int dishId);

    int countAll();

    int countByRestaurantId(int restaurantId);

    int addReview(Review review);

    List<Review> findAllReviewsForAdmin();

    List<Review> findByStatus(int status);

    int updateStatus(Integer id, Integer status);

    int updateLikeCount(Integer id, Integer delta);

    List<Review> findByDishIdWithStatus(Integer dishId);

    Double calculateDishAverageScore(Integer dishId);
}
