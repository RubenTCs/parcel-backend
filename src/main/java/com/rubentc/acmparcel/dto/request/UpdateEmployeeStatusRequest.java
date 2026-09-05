package com.rubentc.acmparcel.dto.request;

import com.rubentc.acmparcel.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeStatusRequest (
        @NotNull
        AccountStatus status
){ }
