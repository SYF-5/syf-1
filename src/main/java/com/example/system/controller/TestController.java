package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.service.AmapGeocodeService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    private final AmapGeocodeService amapGeocodeService;

    public TestController(AmapGeocodeService amapGeocodeService) {
        this.amapGeocodeService = amapGeocodeService;
    }

    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }

    @GetMapping("/geocode/direct")
    public Result<Map<String, Object>> testGeocodeDirect(@RequestParam String address) {
        System.out.println("=== 测试地理编码 Direct ===");
        System.out.println("输入地址: " + address);
        
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Double> location = amapGeocodeService.geocode(address);
            result.put("success", location != null);
            result.put("location", location);
            if (location != null) {
                result.put("longitude", location.get("longitude"));
                result.put("latitude", location.get("latitude"));
            }
            System.out.println("地理编码结果: " + result);
            return Result.success(result);
        } catch (Exception e) {
            System.out.println("地理编码异常: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("error", e.getMessage());
            return Result.error("地理编码失败: " + e.getMessage());
        }
    }

    @GetMapping("/geocode/debug")
    public Result<Map<String, Object>> testGeocodeDebug(@RequestParam String address) {
        System.out.println("\n========== 地理编码调试开始 ==========");
        System.out.println("请求地址: " + address);
        System.out.println("地址长度: " + (address != null ? address.length() : 0));
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 先测试模拟数据
            Map<String, Double> testLocation = amapGeocodeService.testGeocode();
            System.out.println("模拟数据返回: " + testLocation);
            
            // 再调用实际地理编码服务
            Map<String, Double> location = amapGeocodeService.geocode(address);
            
            System.out.println("实际地理编码返回: " + location);
            result.put("inputAddress", address);
            result.put("testResult", testLocation);
            result.put("result", location);
            result.put("success", location != null);
            
            if (location != null) {
                System.out.println("✅ 地理编码成功");
            } else {
                System.out.println("❌ 地理编码返回null");
            }
            
            return Result.success(result);
            
        } catch (Exception e) {
            System.out.println("❌ 地理编码异常: " + e.getMessage());
            e.printStackTrace();
            result.put("success", false);
            result.put("error", e.getMessage());
            return Result.error("地理编码失败: " + e.getMessage());
        } finally {
            System.out.println("========== 地理编码调试结束 ==========\n");
        }
    }
}