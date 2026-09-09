package com.rubentc.acmparcel.employee.dto.request;

import jakarta.validation.constraints.NotNull;


import java.util.Set;
import java.util.UUID;

public record UpdateEmployeeRolesRequest (
        @NotNull
        Set<UUID> roleIds
) { }
