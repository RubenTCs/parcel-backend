package com.rubentc.acmparcel.role.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record CreateRoleRequest(

        @NotBlank
        String name,

        String description,

        Set<UUID> permissionsId
) {
}
