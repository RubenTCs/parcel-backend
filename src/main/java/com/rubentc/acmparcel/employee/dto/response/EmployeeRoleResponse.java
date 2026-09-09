package com.rubentc.acmparcel.employee.dto.response;

import java.util.UUID;

public record EmployeeRoleResponse(
        UUID id,
        String name
) {
}
