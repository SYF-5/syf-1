package com.example.system.dto;

import com.example.system.entity.Favorite;
import com.example.system.entity.Review;
import com.example.system.entity.User;

import java.util.List;

public class UserDetailDTO {
    private User user;
    private List<Review> reviews;
    private List<Favorite> favorites;

    public UserDetailDTO() {
    }

    public UserDetailDTO(User user, List<Review> reviews, List<Favorite> favorites) {
        this.user = user;
        this.reviews = reviews;
        this.favorites = favorites;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }
}
