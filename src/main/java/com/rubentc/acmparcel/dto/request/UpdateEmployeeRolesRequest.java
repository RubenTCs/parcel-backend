package com.rubentc.acmparcel.dto.request;

import jakarta.validation.constraints.NotBlank;


import java.util.Set;
import java.util.UUID;

public record UpdateEmployeeRolesRequest (
        @NotBlank
        Set<UUID> roleIds
) { }
