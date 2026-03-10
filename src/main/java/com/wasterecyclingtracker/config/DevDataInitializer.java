package com.wasterecyclingtracker.config;

import com.wasterecyclingtracker.entity.User;
import com.wasterecyclingtracker.repository.UserRepository;
import com.wasterecyclingtracker.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!prod")
@RequiredArgsConstructor
public class DevDataInitializer {

    private final UserRepository userRepository;
    private final UserManagementService userManagementService;

    @Value("${app.bootstrap.admin.username:admin}")
    private String adminUsername;

    @Value("${app.bootstrap.admin.password:admin123}")
    private String adminPassword;

    @Value("${app.bootstrap.family.username:family}")
    private String familyUsername;

    @Value("${app.bootstrap.family.password:family123}")
    private String familyPassword;

    @Value("${app.bootstrap.center.username:center}")
    private String centerUsername;

    @Value("${app.bootstrap.center.password:center123}")
    private String centerPassword;

    @Bean
    public ApplicationRunner bootstrapUsers() {
        return args -> {
            createUserIfMissing(adminUsername, adminPassword, User.UserRole.ADMIN);
            createUserIfMissing(familyUsername, familyPassword, User.UserRole.FAMILY);
            createUserIfMissing(centerUsername, centerPassword, User.UserRole.CENTER);
        };
    }

    private void createUserIfMissing(String username, String password, User.UserRole role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            userManagementService.createUser(username, password, role);
        }
    }
}