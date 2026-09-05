package com.rubentc.acmparcel.dto.request;

import com.rubentc.acmparcel.enums.AccountStatus;

import java.util.Set;
import java.util.UUID;

public record UpdateEmployeeRequest(
        String name,
        AccountStatus status,
        Set<UUID> roleIds

) {
}
