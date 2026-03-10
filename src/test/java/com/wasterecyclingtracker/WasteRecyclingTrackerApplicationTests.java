package com.wasterecyclingtracker;

import com.wasterecyclingtracker.entity.User;
import com.wasterecyclingtracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WasteRecyclingTrackerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void welcomeEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    void familyEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/family/family"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminEndpointRequiresAdminRole() throws Exception {
        mockMvc.perform(get("/api/admin/users")
                        .with(httpBasic("center", "center123")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/api/admin/users")
                        .with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void bootstrapUsersAreStoredWithHashedPasswords() {
        User adminUser = userRepository.findByUsername("admin").orElseThrow();

        assertThat(adminUser.getPassword()).isNotEqualTo("admin123");
        assertThat(passwordEncoder.matches("admin123", adminUser.getPassword())).isTrue();
        assertThat(adminUser.getRole()).isEqualTo(User.UserRole.ADMIN);
    }
}