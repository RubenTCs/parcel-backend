package com.rubentc.acmparcel.service;

import com.rubentc.acmparcel.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JwtServiceTests {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        String secret = Base64.getEncoder().encodeToString(
                "this-is-a-very-long-secret-key-for-testing".getBytes()
        );

        jwtService = new JwtService(secret);
    }

    @Test
    void shouldGenerateValidToken() {
        UserDetails user = User
                .withUsername("test@example.com")
                .password("password")
                .authorities("employee:read")
                .build();

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertEquals(
                "test@example.com",
                jwtService.extractUsername(token)
        );
        // UserDetails -> JwtService -> JWT -> extract username
    }
}

