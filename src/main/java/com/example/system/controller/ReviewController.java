package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.Review;
import com.example.system.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public Result<List<Review>> getReviews(
            @RequestParam(required = false) Integer restaurantId,
            @RequestParam(required = false) Integer dishId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "3") Integer limit) {
        
        List<Review> reviews;
        if (dishId != null) {
            reviews = reviewService.findByDishId(dishId);
        } else if (restaurantId != null) {
            reviews = reviewService.findByRestaurantIdPaged(restaurantId, offset, limit);
        } else if (status != null) {
            reviews = reviewService.findByStatus(status);
        } else {
            reviews = reviewService.findAllReviewsForAdmin();
        }
        return Result.success(reviews);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Result<List<Review>> getReviewsByRestaurant(@PathVariable Integer restaurantId,
                                                       @RequestParam(defaultValue = "0") Integer offset,
                                                       @RequestParam(defaultValue = "3") Integer limit) {
        List<Review> reviews = reviewService.findByRestaurantIdPaged(restaurantId, offset, limit);
        return Result.success(reviews);
    }

    @GetMapping("/dish/{dishId}")
    public Result<List<Review>> getReviewsByDish(@PathVariable Integer dishId) {
        List<Review> reviews = reviewService.findByDishIdWithStatus(dishId);
        return Result.success(reviews);
    }

    @PutMapping("/{id}/like")
    public Result<String> updateLikeCount(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer delta) {
        int result = reviewService.updateLikeCount(id, delta);
        if (result > 0) {
            return Result.success("点赞成功");
        }
        return Result.error("点赞失败");
    }

    @GetMapping("/count/restaurant/{restaurantId}")
    public Result<Integer> getReviewCountByRestaurant(@PathVariable Integer restaurantId) {
        int count = reviewService.countByRestaurantId(restaurantId);
        return Result.success(count);
    }

    @PostMapping
    public Result<String> addReview(@RequestBody Review review) {
        review.setLikeCount(0);
        review.setStatus(0);
        int result = reviewService.addReview(review);
        if (result > 0) {
            return Result.success("评论成功");
        }
        return Result.error("评论失败");
    }

    @PostMapping("/upload")
    public Result<String> addReviewWithImages(
            @RequestParam("userId") Integer userId,
            @RequestParam("dishId") Integer dishId,
            @RequestParam("content") String content,
            @RequestParam("tasteScore") Integer tasteScore,
            @RequestParam("environmentScore") Integer environmentScore,
            @RequestParam("serviceScore") Integer serviceScore,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        
        try {
            String images = "";
            if (files != null && !files.isEmpty()) {
                StringBuilder imageUrls = new StringBuilder();
                String uploadDir = System.getProperty("user.dir") + "/uploads/reviews/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    if (file != null && !file.isEmpty()) {
                        String originalName = file.getOriginalFilename();
                        if (originalName != null && originalName.contains(".")) {
                            String suffix = originalName.substring(originalName.lastIndexOf("."));
                            String newFileName = UUID.randomUUID().toString() + suffix;
                            File dest = new File(uploadDir + newFileName);
                            file.transferTo(dest);
                            if (i > 0) {
                                imageUrls.append(",");
                            }
                            imageUrls.append("/images/reviews/").append(newFileName);
                        }
                    }
                }
                images = imageUrls.toString();
            }
            
            Review review = new Review();
            review.setUserId(userId);
            review.setDishId(dishId);
            review.setContent(content);
            review.setTasteScore(tasteScore);
            review.setEnvironmentScore(environmentScore);
            review.setServiceScore(serviceScore);
            review.setImages(images);
            review.setLikeCount(0);
            review.setStatus(0);
            
            int result = reviewService.addReview(review);
            if (result > 0) {
                return Result.success("评论成功");
            }
            return Result.error("评论失败");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("图片上传失败");
        }
    }

    @PutMapping("/{id}/status")
    public Result<String> updateReviewStatus(@PathVariable Integer id, @RequestParam Integer status) {
        int result = reviewService.updateStatus(id, status);
        if (result > 0) {
            return Result.success("状态更新成功");
        }
        return Result.error("状态更新失败");
    }
}
