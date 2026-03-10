package com.wasterecyclingtracker.service;

import com.wasterecyclingtracker.dto.StatisticsDTO;
import com.wasterecyclingtracker.entity.FamilyWaste;
import com.wasterecyclingtracker.repository.FamilyWasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final FamilyWasteRepository familyWasteRepository;

    /**
     * Get comprehensive statistics for the dashboard
     */
    public StatisticsDTO getStatistics() {
        List<FamilyWaste> allEntries = familyWasteRepository.findAll();

        StatisticsDTO stats = new StatisticsDTO();

        // Total entries
        stats.setTotalEntries((long) allEntries.size());

        // Total quantity
        stats.setTotalQuantity(allEntries.stream()
                .mapToDouble(FamilyWaste::getQuantity)
                .sum());

        // Count by waste type
        stats.setWasteTypeCount(allEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        FamilyWaste::getWasteType,
                        Collectors.counting()
                )));

        // Total quantity by waste type
        stats.setWasteTypeQuantity(allEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        FamilyWaste::getWasteType,
                        Collectors.summingDouble(FamilyWaste::getQuantity)
                )));

        // Count by status
        stats.setStatusCount(allEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        entry -> entry.getStatus().toString(),
                        Collectors.counting()
                )));

        // Total quantity by status
        stats.setStatusQuantity(allEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        entry -> entry.getStatus().toString(),
                        Collectors.summingDouble(FamilyWaste::getQuantity)
                )));

        // Total unique families
        stats.setTotalFamilies(allEntries.stream()
                .map(FamilyWaste::getFamilyName)
                .distinct()
                .count());

        // Count by individual status
        stats.setPendingEntries(allEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.PENDING)
                .count());

        stats.setProcessingEntries(allEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.PROCESSING)
                .count());

        stats.setRecycledEntries(allEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.RECYCLED)
                .count());

        return stats;
    }

    /**
     * Get statistics for a specific family
     */
    public StatisticsDTO getFamilyStatistics(String familyName) {
        List<FamilyWaste> familyEntries = familyWasteRepository.findByFamilyName(familyName);

        StatisticsDTO stats = new StatisticsDTO();

        stats.setTotalEntries((long) familyEntries.size());
        stats.setTotalQuantity(familyEntries.stream()
                .mapToDouble(FamilyWaste::getQuantity)
                .sum());

        stats.setWasteTypeCount(familyEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        FamilyWaste::getWasteType,
                        Collectors.counting()
                )));

        stats.setWasteTypeQuantity(familyEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        FamilyWaste::getWasteType,
                        Collectors.summingDouble(FamilyWaste::getQuantity)
                )));

        stats.setStatusCount(familyEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        entry -> entry.getStatus().toString(),
                        Collectors.counting()
                )));

        stats.setStatusQuantity(familyEntries.stream()
                .collect(Collectors.groupingByConcurrent(
                        entry -> entry.getStatus().toString(),
                        Collectors.summingDouble(FamilyWaste::getQuantity)
                )));

        stats.setTotalFamilies(1L);
        stats.setPendingEntries(familyEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.PENDING)
                .count());
        stats.setProcessingEntries(familyEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.PROCESSING)
                .count());
        stats.setRecycledEntries(familyEntries.stream()
                .filter(e -> e.getStatus() == FamilyWaste.WasteStatus.RECYCLED)
                .count());

        return stats;
    }
}
