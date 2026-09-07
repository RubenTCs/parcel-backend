package com.rubentc.acmparcel.employee.dto.request;

import com.rubentc.acmparcel.user.entity.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeStatusRequest (
        @NotNull
        AccountStatus status
){ }
