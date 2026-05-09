package com.example.system.service.impl;

import com.example.system.mapper.StatsMapper;
import com.example.system.service.StatsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    private final StatsMapper statsMapper;

    public StatsServiceImpl(StatsMapper statsMapper) {
        this.statsMapper = statsMapper;
    }

    @Override
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", statsMapper.countUsers());
        stats.put("totalStores", statsMapper.countStoresByStatus(1));
        stats.put("totalReviews", statsMapper.countReviews());
        stats.put("pendingStores", statsMapper.countStoresByStatus(0));
        return stats;
    }
}