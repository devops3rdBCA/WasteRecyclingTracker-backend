package com.wasterecyclingtracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (for APIs)
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(Customizer.withDefaults())

                // ❌ Disable default login (VERY IMPORTANT)
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                // Stateless session (for REST API)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Allow preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public endpoints
                        .requestMatchers("/", "/error", "/h2-console/**", "/actuator/health").permitAll()
                        .requestMatchers("/reset-family-password").permitAll()

                        // ✅ Allow login API (IMPORTANT)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Role-based access
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/statistics/family/**").hasAnyRole("FAMILY", "CENTER", "ADMIN")
                        .requestMatchers("/api/statistics/**").hasAnyRole("CENTER", "ADMIN")
                        .requestMatchers("/api/notifications/**").hasAnyRole("CENTER", "ADMIN")
                        .requestMatchers("/api/center/**").hasAnyRole("CENTER", "ADMIN")
                        .requestMatchers("/api/family/**").hasAnyRole("FAMILY", "ADMIN")

                        // All other requests allowed
                        .anyRequest().permitAll()
                )

                // Fix for H2 console
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }

    // Password encoder (for login)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}