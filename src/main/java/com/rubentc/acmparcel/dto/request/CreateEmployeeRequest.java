package com.rubentc.acmparcel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record CreateEmployeeRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String name,

        @NotEmpty
        Set<UUID> roleIds
) {}
