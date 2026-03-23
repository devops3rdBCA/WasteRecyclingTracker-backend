package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.entity.User;
import com.wasterecyclingtracker.repository.UserRepository;
import com.wasterecyclingtracker.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordResetController {
    private final UserRepository userRepository;
    private final UserManagementService userManagementService;

    @PostMapping("/reset-family-password")
    public ResponseEntity<String> resetFamilyPassword() {
        return userRepository.findByUsername("family")
                .map(user -> {
                    userManagementService.updateUser(user.getId(), "family123", User.UserRole.FAMILY);
                    return ResponseEntity.ok("Password reset to family123");
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("Family user not found"));
    }
}
