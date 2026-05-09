package com.example.system.service.impl;

import com.example.system.entity.Restaurant;
import com.example.system.mapper.RestaurantMapper;
import com.example.system.service.AmapGeocodeService;
import com.example.system.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantMapper restaurantMapper;
    private final AmapGeocodeService amapGeocodeService;

    public RestaurantServiceImpl(RestaurantMapper restaurantMapper, AmapGeocodeService amapGeocodeService) {
        this.restaurantMapper = restaurantMapper;
        this.amapGeocodeService = amapGeocodeService;
    }

    @Override
    public List<Restaurant> findByStatus(Integer status) {
        return restaurantMapper.findByStatus(status);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantMapper.findAll();
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantMapper.findById(id);
    }

    @Override
    public List<Restaurant> findByMerchantId(Long merchantId) {
        return restaurantMapper.findByMerchantId(merchantId);
    }

    @Override
    public List<Restaurant> findByCategoryCode(Integer categoryCode) {
        return restaurantMapper.findByCategoryCode(categoryCode);
    }

    @Override
    public List<Restaurant> findByStatusAndCategoryCode(Integer status, Integer categoryCode) {
        return restaurantMapper.findByStatusAndCategoryCode(status, categoryCode);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return restaurantMapper.updateStatus(id, status);
    }

    @Override
    public int insert(Restaurant restaurant) {
        System.out.println("=== RestaurantServiceImpl.insert() ===");
        String originalName = restaurant.getName();
        String originalAddress = restaurant.getAddress();
        System.out.println("门店名称(原始): " + originalName);
        System.out.println("门店地址(原始): " + originalAddress);
        
        String fixedName = fixEncoding(originalName);
        String fixedAddress = fixEncoding(originalAddress);
        
        System.out.println("门店名称(修复后): " + fixedName);
        System.out.println("门店地址(修复后): " + fixedAddress);
        
        restaurant.setName(fixedName);
        restaurant.setAddress(fixedAddress);
        
        if (fixedAddress != null && !fixedAddress.isEmpty()) {
            System.out.println("地址不为空，调用地理编码服务");
            
            Map<String, Double> location = amapGeocodeService.geocode(fixedAddress);
            System.out.println("实际地理编码返回结果: " + location);
            
            if (location == null) {
                System.out.println("实际地理编码返回null，使用模拟数据");
                location = amapGeocodeService.geocodeWithMock(fixedAddress);
            }
            
            if (location != null) {
                restaurant.setLongitude(location.get("longitude"));
                restaurant.setLatitude(location.get("latitude"));
                System.out.println("设置经纬度: 经度=" + restaurant.getLongitude() + ", 纬度=" + restaurant.getLatitude());
            } else {
                System.out.println("地理编码返回null");
            }
        } else {
            System.out.println("地址为空，跳过地理编码");
        }
        
        int result = restaurantMapper.insert(restaurant);
        System.out.println("插入结果: " + result);
        return result;
    }

    private String fixEncoding(String str) {
        if (str == null) {
            return null;
        }
        
        // 如果已经包含中文字符，直接返回
        if (containsChinese(str)) {
            return str;
        }
        
        // 尝试多种编码修复方法
        String result = tryFixUTF8MixedEncoding(str);
        if (result != null && containsChinese(result)) {
            System.out.println("乱码修复成功: " + str + " -> " + result);
            return result;
        }
        
        result = tryFixDoubleUTF8Encoding(str);
        if (result != null && containsChinese(result)) {
            System.out.println("双重UTF-8编码修复成功: " + str + " -> " + result);
            return result;
        }
        
        result = tryFixGBKEncoding(str);
        if (result != null && containsChinese(result)) {
            System.out.println("GBK编码修复成功: " + str + " -> " + result);
            return result;
        }
        
        return str;
    }

    private String tryFixUTF8MixedEncoding(String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private String tryFixDoubleUTF8Encoding(String str) {
        try {
            byte[] firstDecode = str.getBytes(StandardCharsets.ISO_8859_1);
            String firstResult = new String(firstDecode, StandardCharsets.UTF_8);
            byte[] secondDecode = firstResult.getBytes(StandardCharsets.ISO_8859_1);
            return new String(secondDecode, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private String tryFixGBKEncoding(String str) {
        try {
            byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
            return new String(bytes, Charset.forName("GBK"));
        } catch (Exception e) {
            return null;
        }
    }

    private boolean containsChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int updateImage(Long id, String imageUrl) {
        return restaurantMapper.updateImage(id, imageUrl);
    }

    @Override
    public void updateTotalScore(Long id, Double totalScore) {
        restaurantMapper.updateTotalScore(id, totalScore);
    }

    @Override
    public Double calculateRestaurantAverageScore(Long restaurantId) {
        return restaurantMapper.calculateRestaurantAverageScore(restaurantId);
    }
}