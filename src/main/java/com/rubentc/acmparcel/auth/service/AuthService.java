package com.rubentc.acmparcel.auth.service;

import com.rubentc.acmparcel.auth.dto.LoginRequest;
import com.rubentc.acmparcel.auth.dto.LoginResponse;
import com.rubentc.acmparcel.auth.dto.SetPasswordRequest;
import com.rubentc.acmparcel.auth.entity.Invitation;
import com.rubentc.acmparcel.common.exception.CustomException;
import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final InvitationService invitationService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(@Valid LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(token);
    }

    @Transactional
    public void setPassword(@Valid SetPasswordRequest request) {

        if (!request.password().equals(request.confirmPassword())) {
            throw new CustomException("Passwords don't match");
        }

        Invitation invitation =
                invitationService.findByToken(request.token());

        if (invitation.getUsedAt() != null) {
            throw new CustomException("Invalid Invitation");
        }

        if (invitation.getExpiresAt().isBefore(Instant.now())) {
            throw new CustomException("Invalid Invitation");
        }

        User user = invitation.getUser();

        if (user.getStatus() != AccountStatus.PENDING) {
            throw new CustomException("Invalid Invitation");
        }

        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );

        user.setStatus(AccountStatus.ACTIVE);

        invitation.setUsedAt(Instant.now());
    }
}
