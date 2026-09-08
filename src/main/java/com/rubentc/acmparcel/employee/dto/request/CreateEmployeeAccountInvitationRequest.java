package com.rubentc.acmparcel.employee.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record CreateEmployeeAccountInvitationRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String name,

        Set<UUID> roleIds
) {}
