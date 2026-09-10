package com.rubentc.acmparcel.role.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record UpdateRolePermissionRequest(
        @NotNull
        Set<UUID> permissionsIds
) {
}
