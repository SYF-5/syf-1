package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.entity.Restaurant;
import com.example.system.service.AmapGeocodeService;
import com.example.system.service.RestaurantService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final AmapGeocodeService amapGeocodeService;

    public RestaurantController(RestaurantService restaurantService, AmapGeocodeService amapGeocodeService) {
        this.restaurantService = restaurantService;
        this.amapGeocodeService = amapGeocodeService;
    }
    
    @GetMapping("/geocode/test")
    public Result<Map<String, Double>> testGeocode(@RequestParam String address) {
        System.out.println("=== 测试地理编码 ===");
        System.out.println("测试地址: " + address);
        Map<String, Double> result = amapGeocodeService.geocode(address);
        System.out.println("地理编码结果: " + result);
        return Result.success(result);
    }

    @GetMapping
    public Result<List<Restaurant>> getRestaurants(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer categoryCode) {
        System.out.println("查询参数 - status: " + status + ", categoryCode: " + categoryCode);
        List<Restaurant> restaurants;
        if (status != null && categoryCode != null) {
            System.out.println("执行查询: status=" + status + ", categoryCode=" + categoryCode);
            restaurants = restaurantService.findByStatusAndCategoryCode(status, categoryCode);
        } else if (categoryCode != null) {
            System.out.println("执行查询: categoryCode=" + categoryCode);
            restaurants = restaurantService.findByCategoryCode(categoryCode);
        } else if (status != null) {
            System.out.println("执行查询: status=" + status);
            restaurants = restaurantService.findByStatus(status);
        } else {
            System.out.println("执行查询: findAll");
            restaurants = restaurantService.findAll();
        }
        System.out.println("查询结果数量: " + restaurants.size());
        return Result.success(restaurants);
    }

    @GetMapping("/{id}")
    public Result<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant != null) {
            return Result.success(restaurant);
        }
        return Result.error("门店不存在");
    }

    @GetMapping("/merchant/{merchantId}")
    public Result<List<Restaurant>> getRestaurantsByMerchantId(@PathVariable Long merchantId) {
        List<Restaurant> restaurants = restaurantService.findByMerchantId(merchantId);
        return Result.success(restaurants);
    }

    @PostMapping
    public Result<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        System.out.println("=== RestaurantController.createRestaurant() ===");
        String name = restaurant.getName();
        String address = restaurant.getAddress();
        System.out.println("收到创建门店请求，name=" + name + ", address=" + address);
        
        // 调试：检查字符串的字节表示
        if (name != null) {
            System.out.println("name字节长度: " + name.getBytes().length + ", 字符长度: " + name.length());
        }
        if (address != null) {
            System.out.println("address字节长度: " + address.getBytes().length + ", 字符长度: " + address.length());
        }
        
        restaurant.setStatus(0);
        int result = restaurantService.insert(restaurant);
        System.out.println("门店插入结果: " + result + ", 插入后的经纬度 - longitude=" + restaurant.getLongitude() + ", latitude=" + restaurant.getLatitude());
        if (result > 0) {
            return Result.success(restaurant);
        }
        return Result.error("门店创建失败");
    }

    @PutMapping("/{id}/audit")
    public Result<String> auditRestaurant(@PathVariable Long id, @RequestParam Integer status) {
        int result = restaurantService.updateStatus(id, status);
        if (result > 0) {
            return Result.success("审核成功");
        }
        return Result.error("审核失败");
    }

    @PostMapping("/{restaurantId}/image")
    public Result<String> uploadImage(@PathVariable Long restaurantId, @RequestParam("file") MultipartFile file) {
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

            String uploadDir = System.getProperty("user.dir") + "/uploads/restaurants/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    return Result.error("无法创建上传目录");
                }
            }

            File dest = new File(uploadDir + newFileName);
            file.transferTo(dest);

            String imageUrl = "/images/restaurants/" + newFileName;
            int result = restaurantService.updateImage(restaurantId, imageUrl);

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
