package com.rubentc.acmparcel.employee.dto.request;

import jakarta.validation.constraints.NotBlank;


import java.util.Set;
import java.util.UUID;

public record UpdateEmployeeRolesRequest (
        @NotBlank
        Set<UUID> roleIds
) { }
