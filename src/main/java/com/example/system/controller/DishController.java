package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.Dish;
import com.example.system.service.DishService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public Result<List<Dish>> getDishes(@RequestParam(required = false) Integer status) {
        List<Dish> dishes;
        if (status != null) {
            dishes = dishService.findByStatus(status);
        } else {
            dishes = dishService.findAll();
        }
        return Result.success(dishes);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Result<List<Dish>> getDishesByRestaurantId(@PathVariable Integer restaurantId) {
        List<Dish> dishes = dishService.findByRestaurantId(restaurantId);
        return Result.success(dishes);
    }

    @GetMapping("/{id}")
    public Result<Dish> getDishById(@PathVariable Integer id) {
        Dish dish = dishService.findById(id);
        if (dish != null) {
            return Result.success(dish);
        }
        return Result.error("菜品不存在");
    }

    @PostMapping
    public Result<Dish> createDish(@RequestBody Dish dish) {
        Dish createdDish = dishService.save(dish);
        return Result.success(createdDish);
    }

    @PutMapping("/{id}/audit")
    public Result<String> auditDish(@PathVariable Integer id, @RequestParam Integer status) {
        int result = dishService.updateStatus(id, status);
        if (result > 0) {
            return Result.success("审核成功");
        }
        return Result.error("审核失败");
    }

    @PostMapping("/{dishId}/image")
    public Result<String> uploadImage(@PathVariable Integer dishId, @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只支持图片格式");
            }

            String originalName = file.getOriginalFilename();
            if (originalName == null || !originalName.contains(".")) {
                return Result.error("文件名无效");
            }

            String suffix = originalName.substring(originalName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;

            String uploadDir = System.getProperty("user.dir") + "/uploads/dishes/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    return Result.error("无法创建上传目录");
                }
            }

            File dest = new File(uploadDir + newFileName);
            file.transferTo(dest);

            String imageUrl = "/images/dishes/" + newFileName;
            int result = dishService.updateImage(dishId, imageUrl);

            if (result > 0) {
                return Result.success(imageUrl);
            }
            return Result.error("图片上传失败，无法更新数据库");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件保存失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败: " + e.getMessage());
        }
    }
}
