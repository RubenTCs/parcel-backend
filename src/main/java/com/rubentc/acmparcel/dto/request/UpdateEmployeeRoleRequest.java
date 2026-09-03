package com.rubentc.acmparcel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UpdateEmployeeRoleRequest {

    @NotEmpty
    private Set<UUID> roleIds;
}
