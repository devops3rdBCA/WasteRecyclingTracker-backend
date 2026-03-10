package com.wasterecyclingtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private Long totalEntries;
    private Double totalQuantity;
    private Map<String, Long> wasteTypeCount;
    private Map<String, Double> wasteTypeQuantity;
    private Map<String, Long> statusCount;
    private Map<String, Double> statusQuantity;
    private Long totalFamilies;
    private Long pendingEntries;
    private Long processingEntries;
    private Long recycledEntries;
}
