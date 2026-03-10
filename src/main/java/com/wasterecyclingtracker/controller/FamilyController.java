package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.entity.FamilyWaste;
import com.wasterecyclingtracker.service.FamilyWasteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FamilyController {

    private final FamilyWasteService familyWasteService;

    /**
     * POST /api/family - Add a new waste entry
     */
    @PostMapping
    public ResponseEntity<FamilyWaste> addWasteEntry(@RequestBody FamilyWaste waste) {
        try {
            FamilyWaste saved = familyWasteService.addWasteEntry(waste);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * GET /api/family/{familyName} - Get all waste entries for a family
     */
    @GetMapping("/{familyName}")
    public ResponseEntity<List<FamilyWaste>> getFamilyEntries(@PathVariable String familyName) {
        try {
            List<FamilyWaste> entries = familyWasteService.getFamilyEntries(familyName);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/family/entry/{id} - Get a specific waste entry by ID
     */
    @GetMapping("/entry/{id}")
    public ResponseEntity<FamilyWaste> getWasteEntry(@PathVariable Long id) {
        try {
            return familyWasteService.getWasteEntryById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/family/{id} - Update a waste entry
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyWaste> updateWasteEntry(@PathVariable Long id, @RequestBody FamilyWaste waste) {
        try {
            FamilyWaste updated = familyWasteService.updateWasteEntry(id, waste);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/family/{id} - Delete a waste entry
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWasteEntry(@PathVariable Long id) {
        try {
            familyWasteService.deleteWasteEntry(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/family/status/{status} - Get entries by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FamilyWaste>> getEntriesByStatus(@PathVariable String status) {
        try {
            FamilyWaste.WasteStatus wasteStatus = FamilyWaste.WasteStatus.valueOf(status.toUpperCase());
            List<FamilyWaste> entries = familyWasteService.getEntriesByStatus(wasteStatus);
            return ResponseEntity.ok(entries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
