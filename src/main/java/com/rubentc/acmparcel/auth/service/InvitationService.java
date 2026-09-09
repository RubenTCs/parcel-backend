package com.rubentc.acmparcel.auth.service;

import com.rubentc.acmparcel.auth.repository.InvitationRepository;
import com.rubentc.acmparcel.auth.entity.Invitation;
import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;

    public String createInvitation(User user) {
        String rawToken = UUID.randomUUID().toString();

        Invitation invitation = Invitation.builder()
                .user(user)
                .tokenHash(hashToken(rawToken))
                .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
                .createdAt(Instant.now())
                .build();

        invitationRepository.save(invitation);

        return rawToken;
    }

        public Invitation findByToken(String rawToken) {
        String tokenHash = hashToken(rawToken);

        return invitationRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation Token not found"));
    }

    private String hashToken(String token) {
        return DigestUtils.sha256Hex(token);
    }

}
