package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.Favorite;
import com.example.system.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public Result<List<Favorite>> getFavorites(@RequestParam Integer userId) {
        List<Favorite> favorites = favoriteService.findByUserIdWithRestaurant(userId);
        return Result.success(favorites);
    }

    @PostMapping
    public Result<Favorite> addFavorite(@RequestBody Favorite favorite) {
        Favorite result = favoriteService.addFavorite(favorite);
        return Result.success(result);
    }

    @DeleteMapping("/{id}")
    public Result<String> removeFavorite(@PathVariable Integer id) {
        favoriteService.removeFavorite(id);
        return Result.success("取消收藏成功");
    }
}