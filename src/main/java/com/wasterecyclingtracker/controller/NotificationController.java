package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {

    private final EmailNotificationService emailNotificationService;

    /**
     * POST /api/notifications/status-change - Send status change notification
     */
    @PostMapping("/status-change")
    public ResponseEntity<Map<String, String>> sendStatusChangeNotification(@RequestBody Map<String, String> request) {
        try {
            String familyName = request.get("familyName");
            String wasteType = request.get("wasteType");
            String oldStatus = request.get("oldStatus");
            String newStatus = request.get("newStatus");

            emailNotificationService.sendStatusChangeNotification(familyName, wasteType, oldStatus, newStatus);
            return ResponseEntity.ok(Map.of("message", "Notification sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/notifications/pickup - Send pickup notification
     */
    @PostMapping("/pickup")
    public ResponseEntity<Map<String, String>> sendPickupNotification(@RequestBody Map<String, Object> request) {
        try {
            String familyName = (String) request.get("familyName");
            Double quantity = ((Number) request.get("quantity")).doubleValue();

            emailNotificationService.sendPickupNotification(familyName, quantity);
            return ResponseEntity.ok(Map.of("message", "Pickup notification sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/notifications/recycling-completed - Send recycling completed notification
     */
    @PostMapping("/recycling-completed")
    public ResponseEntity<Map<String, String>> sendRecyclingCompletedNotification(@RequestBody Map<String, Object> request) {
        try {
            String familyName = (String) request.get("familyName");
            String wasteType = (String) request.get("wasteType");
            Double quantity = ((Number) request.get("quantity")).doubleValue();

            emailNotificationService.sendRecyclingCompletedNotification(familyName, wasteType, quantity);
            return ResponseEntity.ok(Map.of("message", "Recycling notification sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/notifications/weekly-summary - Send weekly summary
     */
    @PostMapping("/weekly-summary")
    public ResponseEntity<Map<String, String>> sendWeeklySummary(@RequestBody Map<String, Object> request) {
        try {
            String familyName = (String) request.get("familyName");
            Long totalEntries = ((Number) request.get("totalEntries")).longValue();
            Double totalQuantity = ((Number) request.get("totalQuantity")).doubleValue();

            emailNotificationService.sendWeeklySummary(familyName, totalEntries, totalQuantity);
            return ResponseEntity.ok(Map.of("message", "Weekly summary sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/notifications/admin-alert - Send admin alert
     */
    @PostMapping("/admin-alert")
    public ResponseEntity<Map<String, String>> sendAdminAlert(@RequestBody Map<String, Object> request) {
        try {
            Long pendingCount = ((Number) request.get("pendingCount")).longValue();
            Long totalQuantity = ((Number) request.get("totalQuantity")).longValue();

            emailNotificationService.sendAdminAlert(pendingCount, totalQuantity);
            return ResponseEntity.ok(Map.of("message", "Admin alert sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
