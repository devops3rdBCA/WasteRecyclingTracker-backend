package com.wasterecyclingtracker.service;

import com.wasterecyclingtracker.entity.FamilyWaste;
import com.wasterecyclingtracker.repository.FamilyWasteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterService {

    private final FamilyWasteRepository familyWasteRepository;

    /**
     * Get all waste entries from all families
     */
    public List<FamilyWaste> getAllEntries() {
        return familyWasteRepository.findAll();
    }

    /**
     * Update the status of a waste entry
     */
    public FamilyWaste updateEntryStatus(Long id, FamilyWaste.WasteStatus status) {
        return familyWasteRepository.findById(id).map(existing -> {
            existing.setStatus(status);
            return familyWasteRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Waste entry not found with id: " + id));
    }

    /**
     * Delete a recycled entry
     */
    public void deleteRecycledEntry(Long id) {
        FamilyWaste waste = familyWasteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waste entry not found with id: " + id));

        if (waste.getStatus() != FamilyWaste.WasteStatus.RECYCLED) {
            throw new RuntimeException("Only recycled entries can be deleted");
        }

        familyWasteRepository.deleteById(id);
    }

    /**
     * Get entries by status
     */
    public List<FamilyWaste> getEntriesByStatus(FamilyWaste.WasteStatus status) {
        return familyWasteRepository.findByStatus(status);
    }

    /**
     * Get entries by family name
     */
    public List<FamilyWaste> getEntriesByFamilyName(String familyName) {
        return familyWasteRepository.findByFamilyName(familyName);
    }
}
