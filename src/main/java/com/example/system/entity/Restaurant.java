package com.example.system.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Restaurant {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private Double avgPrice;
    private String businessHours;
    private String phone;
    private String address;
    private Integer categoryCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 手动添加getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getAvgPrice() { return avgPrice; }
    public void setAvgPrice(Double avgPrice) { this.avgPrice = avgPrice; }
    
    public String getBusinessHours() { return businessHours; }
    public void setBusinessHours(String businessHours) { this.businessHours = businessHours; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Integer getCategoryCode() { return categoryCode; }
    public void setCategoryCode(Integer categoryCode) { this.categoryCode = categoryCode; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}