package com.rubentc.acmparcel.role.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rubentc.acmparcel.permission.dto.PermissionResponse;

import java.time.Instant;
import java.util.Set;

public record CreateRoleResponse(
        String name,
        String description,
        Set<PermissionResponse> permissions,
        Instant createdAt
) {
}
