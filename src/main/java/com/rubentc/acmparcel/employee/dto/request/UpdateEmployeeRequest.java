package com.rubentc.acmparcel.employee.dto.request;

import com.rubentc.acmparcel.user.entity.AccountStatus;

import java.util.Set;
import java.util.UUID;

public record UpdateEmployeeRequest(
        String name,
        AccountStatus status,
        Set<UUID> roleIds

) {
}
