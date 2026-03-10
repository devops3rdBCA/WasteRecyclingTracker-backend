package com.wasterecyclingtracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    /**
     * Send notification when waste status changes
     * In production, use JavaMailSender with SMTP configuration
     */
    public void sendStatusChangeNotification(String familyName, String wasteType, String oldStatus, String newStatus) {
        String message = String.format(
                "Dear %s,\n\n" +
                "Your waste entry has been updated:\n" +
                "Waste Type: %s\n" +
                "Status: %s → %s\n\n" +
                "Thank you for your contribution to recycling!\n" +
                "Best regards,\nWaste Recycling Tracker Team",
                familyName, wasteType, oldStatus, newStatus
        );

        // Log the notification (in production, send via email)
        logger.info("NOTIFICATION: {}", message);
    }

    /**
     * Send notification when waste is ready for pickup
     */
    public void sendPickupNotification(String familyName, Double quantity) {
        String message = String.format(
                "Dear %s,\n\n" +
                "Your waste collection is scheduled!\n" +
                "Total Quantity: %.2f kg\n\n" +
                "Please have your waste ready for pickup.\n" +
                "Best regards,\nWaste Recycling Tracker Team",
                familyName, quantity
        );

        logger.info("PICKUP NOTIFICATION: {}", message);
    }

    /**
     * Send notification when waste is recycled
     */
    public void sendRecyclingCompletedNotification(String familyName, String wasteType, Double quantity) {
        String message = String.format(
                "Dear %s,\n\n" +
                "Your waste has been successfully recycled!\n" +
                "Waste Type: %s\n" +
                "Quantity: %.2f kg\n\n" +
                "Thank you for contributing to environmental conservation!\n" +
                "Best regards,\nWaste Recycling Tracker Team",
                familyName, wasteType, quantity
        );

        logger.info("RECYCLING COMPLETED NOTIFICATION: {}", message);
    }

    /**
     * Send summary report to family
     */
    public void sendWeeklySummary(String familyName, Long totalEntries, Double totalQuantity) {
        String message = String.format(
                "Dear %s,\n\n" +
                "Your Weekly Waste Summary:\n" +
                "Total Entries: %d\n" +
                "Total Quantity: %.2f kg\n\n" +
                "Keep up the good work!\n" +
                "Best regards,\nWaste Recycling Tracker Team",
                familyName, totalEntries, totalQuantity
        );

        logger.info("WEEKLY SUMMARY: {}", message);
    }

    /**
     * Send admin alert for pending entries
     */
    public void sendAdminAlert(Long pendingCount, Long totalQuantity) {
        String message = String.format(
                "Admin Alert:\n\n" +
                "Pending waste entries: %d\n" +
                "Total quantity waiting for processing: %d kg\n\n" +
                "Please review and process these entries.\n" +
                "Best regards,\nWaste Recycling Tracker System",
                pendingCount, totalQuantity
        );

        logger.info("ADMIN ALERT: {}", message);
    }
}
