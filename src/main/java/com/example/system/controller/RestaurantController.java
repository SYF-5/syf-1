package com.example.system.controller;

import com.example.system.entity.Restaurant;
import com.example.system.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    
    @GetMapping
    public List<Restaurant> findAll() {
        return restaurantService.findAll();
    }
    
    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }
    
    @GetMapping("/test-db")
    public String testDb() {
        String url = "jdbc:mysql://localhost:3306/login_db?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "040129";
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM restaurant LIMIT 1")) {
            
            if (rs.next()) {
                return "Database connection successful. Found restaurant: " + rs.getString("name");
            } else {
                return "Database connection successful, but no restaurants found.";
            }
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}