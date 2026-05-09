package com.example.system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class AmapGeocodeService {

    private static final Logger logger = LoggerFactory.getLogger(AmapGeocodeService.class);

    private final String apiKey;
    private final RestTemplate restTemplate;

    public AmapGeocodeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.apiKey = "640e6e3041885522ec573521f426bfcd";
        logger.info("AmapGeocodeService 初始化完成，API Key配置: {}", apiKey != null && !apiKey.isEmpty() ? "已配置" : "未配置");
        if (apiKey != null && !apiKey.isEmpty()) {
            logger.info("API Key: {}", apiKey);
        }
    }

    public Map<String, Double> geocode(String address) {
        logger.info("=== AmapGeocodeService.geocode() ===");
        logger.info("输入地址: {}", address);
        logger.info("API Key配置: {}", apiKey != null && !apiKey.isEmpty() ? "已配置" : "未配置");
        
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("错误: API Key为空！");
            return null;
        }
        
        if (address == null || address.isEmpty()) {
            logger.error("错误: 地址为空！");
            return null;
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
            String url = String.format(
                "http://restapi.amap.com/v3/geocode/geo?key=%s&address=%s",
                apiKey, encodedAddress
            );
            logger.info("请求URL: {}", url);

            logger.info("开始调用RestTemplate...");
            long startTime = System.currentTimeMillis();
            
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            long endTime = System.currentTimeMillis();
            logger.info("RestTemplate调用完成，耗时: {}ms，响应状态码: {}", (endTime - startTime), response.getStatusCode());
            
            String responseBody = response.getBody();
            logger.info("响应体长度: {} 字符", responseBody != null ? responseBody.length() : 0);
            
            if (responseBody == null || responseBody.isEmpty()) {
                logger.error("错误: 响应体为空！");
                return null;
            }
            
            logger.info("响应体内容: {}", responseBody);

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String status = jsonNode.has("status") ? jsonNode.get("status").asText() : "未知";
            logger.info("地理编码状态: {}", status);

            if (!"1".equals(status)) {
                String info = jsonNode.has("info") ? jsonNode.get("info").asText() : "未知错误";
                String infocode = jsonNode.has("infocode") ? jsonNode.get("infocode").asText() : "未知";
                logger.error("地理编码失败 - info: {}, infocode: {}", info, infocode);
                return null;
            }

            JsonNode geocodes = jsonNode.get("geocodes");
            if (geocodes != null && geocodes.isArray() && geocodes.size() > 0) {
                JsonNode firstGeocode = geocodes.get(0);
                String location = firstGeocode.has("location") ? firstGeocode.get("location").asText() : "";
                logger.info("解析到的经纬度: {}", location);
                
                if (!location.isEmpty()) {
                    String[] lngLat = location.split(",");
                    if (lngLat.length == 2) {
                        Map<String, Double> result = new HashMap<>();
                        result.put("longitude", Double.parseDouble(lngLat[0]));
                        result.put("latitude", Double.parseDouble(lngLat[1]));
                        logger.info("地理编码成功: 经度={}, 纬度={}", result.get("longitude"), result.get("latitude"));
                        return result;
                    } else {
                        logger.error("错误: 经纬度格式不正确，期望2个元素，实际{}个", lngLat.length);
                    }
                } else {
                    logger.error("错误: location字段为空");
                }
            } else {
                logger.info("未找到地理编码结果");
            }
        } catch (RestClientException e) {
            logger.error("REST请求失败: {}", e.getMessage(), e);
            System.out.println("REST请求失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("地理编码失败: {}", e.getMessage(), e);
            System.out.println("地理编码失败: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Double> testGeocode() {
        logger.info("=== 测试地理编码（模拟数据）===");
        Map<String, Double> result = new HashMap<>();
        result.put("longitude", 116.468615);
        result.put("latitude", 39.993126);
        logger.info("返回模拟经纬度: 经度={}, 纬度={}", result.get("longitude"), result.get("latitude"));
        return result;
    }
    
    // 临时方法：直接返回模拟数据用于测试
    public Map<String, Double> geocodeWithMock(String address) {
        logger.info("=== 使用模拟数据 ===");
        logger.info("输入地址: {}", address);
        Map<String, Double> result = new HashMap<>();
        result.put("longitude", 116.468615);
        result.put("latitude", 39.993126);
        logger.info("返回模拟经纬度: 经度={}, 纬度={}", result.get("longitude"), result.get("latitude"));
        return result;
    }
}