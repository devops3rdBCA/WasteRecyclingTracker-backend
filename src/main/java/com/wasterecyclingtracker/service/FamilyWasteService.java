package com.wasterecyclingtracker.service;

import com.wasterecyclingtracker.entity.FamilyWaste;
import com.wasterecyclingtracker.repository.FamilyWasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FamilyWasteService {

    private final FamilyWasteRepository familyWasteRepository;

    /**
     * Add a new waste entry for a family
     */
    public FamilyWaste addWasteEntry(FamilyWaste waste) {
        waste.setStatus(FamilyWaste.WasteStatus.PENDING);
        return familyWasteRepository.save(waste);
    }

    /**
     * Get all waste entries for a specific family
     */
    public List<FamilyWaste> getFamilyEntries(String familyName) {
        return familyWasteRepository.findByFamilyName(familyName);
    }

    /**
     * Get a specific waste entry by ID
     */
    public Optional<FamilyWaste> getWasteEntryById(Long id) {
        return familyWasteRepository.findById(id);
    }

    /**
     * Update a waste entry
     */
    public FamilyWaste updateWasteEntry(Long id, FamilyWaste waste) {
        return familyWasteRepository.findById(id).map(existing -> {
            existing.setFamilyName(waste.getFamilyName());
            existing.setWasteType(waste.getWasteType());
            existing.setQuantity(waste.getQuantity());
            return familyWasteRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Waste entry not found with id: " + id));
    }

    /**
     * Delete a waste entry
     */
    public void deleteWasteEntry(Long id) {
        if (!familyWasteRepository.existsById(id)) {
            throw new RuntimeException("Waste entry not found with id: " + id);
        }
        familyWasteRepository.deleteById(id);
    }

    /**
     * Get all entries with a specific status
     */
    public List<FamilyWaste> getEntriesByStatus(FamilyWaste.WasteStatus status) {
        return familyWasteRepository.findByStatus(status);
    }
}
