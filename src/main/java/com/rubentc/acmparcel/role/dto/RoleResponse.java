package com.rubentc.acmparcel.role.dto;

import com.rubentc.acmparcel.permission.dto.PermissionResponse;

import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description,
        Set<PermissionResponse> permissions
) {
}
