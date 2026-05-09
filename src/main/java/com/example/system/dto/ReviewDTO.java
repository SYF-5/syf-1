package com.example.system.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private Long user_id;
    private Long dish_id;
    private String dish_name;
    private Long restaurant_id;
    private String restaurant_name;
    private String content;
    private String images;
    private Integer taste_score;
    private Integer environment_score;
    private Integer service_score;
    private Integer like_count;
    private Integer status;
    private LocalDateTime created_at;

    public ReviewDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getDish_id() {
        return dish_id;
    }

    public void setDish_id(Long dish_id) {
        this.dish_id = dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getTaste_score() {
        return taste_score;
    }

    public void setTaste_score(Integer taste_score) {
        this.taste_score = taste_score;
    }

    public Integer getEnvironment_score() {
        return environment_score;
    }

    public void setEnvironment_score(Integer environment_score) {
        this.environment_score = environment_score;
    }

    public Integer getService_score() {
        return service_score;
    }

    public void setService_score(Integer service_score) {
        this.service_score = service_score;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
