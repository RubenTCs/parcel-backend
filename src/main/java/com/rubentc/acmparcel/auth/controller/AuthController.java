package com.rubentc.acmparcel.auth.controller;

import com.rubentc.acmparcel.auth.dto.LoginRequest;
import com.rubentc.acmparcel.auth.dto.LoginResponse;
import com.rubentc.acmparcel.auth.dto.SetPasswordRequest;
import com.rubentc.acmparcel.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/set-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setPassword(@Valid @RequestBody SetPasswordRequest request) {

        authService.setPassword(request);
    }
}
