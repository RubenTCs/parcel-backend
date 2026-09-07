package com.rubentc.acmparcel;

import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        User user = new User();

        user.setEmail("test@example.com");
        user.setPasswordHash(
                passwordEncoder.encode("password123")
        );
        user.setStatus(AccountStatus.ACTIVE);

        userRepository.save(user);
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {

        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "email": "test@example.com",
                                "password": "password123"
                            }
                            """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}