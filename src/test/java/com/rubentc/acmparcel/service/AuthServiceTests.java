package com.rubentc.acmparcel.service;

import com.rubentc.acmparcel.auth.dto.LoginRequest;
import com.rubentc.acmparcel.auth.dto.LoginResponse;
import com.rubentc.acmparcel.auth.service.AuthService;
import com.rubentc.acmparcel.auth.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request =
                new LoginRequest(
                        "test@example.com",
                        "password"
                );

        UserDetails userDetails =
                User.withUsername("test@example.com")
                        .password("hashed-password")
                        .authorities("employee:read")
                        .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(jwtService.generateToken(userDetails))
                .thenReturn("jwt-token");

        LoginResponse response =
                authService.login(request);

        assertEquals("jwt-token", response.token());

        verify(authenticationManager)
                .authenticate(any());

        verify(jwtService)
                .generateToken(userDetails);

        //Login Request -> AuthService -> AuthenticationManager -> JwtService -> LoginResponse
    }
}