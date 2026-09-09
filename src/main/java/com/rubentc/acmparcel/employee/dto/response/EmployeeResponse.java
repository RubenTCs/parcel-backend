package com.rubentc.acmparcel.employee.dto.response;


import com.rubentc.acmparcel.user.entity.AccountStatus;

import java.util.Set;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        UUID userId,
        String email,
        String name,
        AccountStatus status,
        Set<EmployeeRoleResponse> roles
) {
}
