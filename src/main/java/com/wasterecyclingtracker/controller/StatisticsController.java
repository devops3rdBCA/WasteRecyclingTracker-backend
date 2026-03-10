package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.dto.StatisticsDTO;
import com.wasterecyclingtracker.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class StatisticsController {

    private final StatisticsService statisticsService;

    /**
     * GET /api/statistics - Get overall statistics
     */
    @GetMapping
    public ResponseEntity<StatisticsDTO> getStatistics() {
        StatisticsDTO stats = statisticsService.getStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/statistics/family/{familyName} - Get family-specific statistics
     */
    @GetMapping("/family/{familyName}")
    public ResponseEntity<StatisticsDTO> getFamilyStatistics(@PathVariable String familyName) {
        StatisticsDTO stats = statisticsService.getFamilyStatistics(familyName);
        return ResponseEntity.ok(stats);
    }
}
