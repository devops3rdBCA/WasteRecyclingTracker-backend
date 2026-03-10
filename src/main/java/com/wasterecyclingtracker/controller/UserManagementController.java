package com.wasterecyclingtracker.controller;

import com.wasterecyclingtracker.entity.User;
import com.wasterecyclingtracker.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserManagementController {

    private final UserManagementService userManagementService;

    /**
     * GET /api/admin/users - Get all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userManagementService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/admin/users - Create a new user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");
            String roleStr = request.get("role");

            if (username == null || password == null || roleStr == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            User.UserRole role = User.UserRole.valueOf(roleStr.toUpperCase());
            User newUser = userManagementService.createUser(username, password, role);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/admin/users/{id} - Get user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            return userManagementService.getUserById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/admin/users/{id} - Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String password = request.get("password");
            String roleStr = request.get("role");
            User.UserRole role = roleStr != null ? User.UserRole.valueOf(roleStr.toUpperCase()) : null;

            User updatedUser = userManagementService.updateUser(id, password, role);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/admin/users/{id} - Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userManagementService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/admin/users/families - Get all family users
     */
    @GetMapping("/families/list")
    public ResponseEntity<List<User>> getFamilyUsers() {
        try {
            List<User> families = userManagementService.getFamilyUsers();
            return ResponseEntity.ok(families);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/admin/users/centers - Get all center users
     */
    @GetMapping("/centers/list")
    public ResponseEntity<List<User>> getCenterUsers() {
        try {
            List<User> centers = userManagementService.getCenterUsers();
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
