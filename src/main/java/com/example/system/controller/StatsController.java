package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Result<Map<String, Long>> getStats() {
        Map<String, Long> stats = statsService.getStats();
        return Result.success(stats);
    }
}