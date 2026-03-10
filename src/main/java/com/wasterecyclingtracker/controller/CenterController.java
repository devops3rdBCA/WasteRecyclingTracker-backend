package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.entity.FamilyWaste;
import com.wasterecyclingtracker.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/center")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CenterController {

    private final CenterService centerService;

    /**
     * GET /api/center - Get all waste entries
     */
    @GetMapping
    public ResponseEntity<List<FamilyWaste>> getAllEntries() {
        try {
            List<FamilyWaste> entries = centerService.getAllEntries();
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/center/status/{status} - Get entries by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FamilyWaste>> getEntriesByStatus(@PathVariable String status) {
        try {
            FamilyWaste.WasteStatus wasteStatus = FamilyWaste.WasteStatus.valueOf(status.toUpperCase());
            List<FamilyWaste> entries = centerService.getEntriesByStatus(wasteStatus);
            return ResponseEntity.ok(entries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/center/family/{familyName} - Get entries for a specific family
     */
    @GetMapping("/family/{familyName}")
    public ResponseEntity<List<FamilyWaste>> getEntriesByFamilyName(@PathVariable String familyName) {
        try {
            List<FamilyWaste> entries = centerService.getEntriesByFamilyName(familyName);
            return ResponseEntity.ok(entries);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/center/{id} - Update the status of a waste entry
     */
    @PutMapping("/{id}")
    public ResponseEntity<FamilyWaste> updateEntryStatus(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        try {
            String statusStr = requestBody.get("status");
            if (statusStr == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            FamilyWaste.WasteStatus status = FamilyWaste.WasteStatus.valueOf(statusStr.toUpperCase());
            FamilyWaste updated = centerService.updateEntryStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/center/{id} - Delete a recycled entry
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecycledEntry(@PathVariable Long id) {
        try {
            centerService.deleteRecycledEntry(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
