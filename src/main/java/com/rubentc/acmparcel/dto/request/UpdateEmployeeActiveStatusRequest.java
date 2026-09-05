package com.rubentc.acmparcel.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEmployeeActiveStatusRequest {

    @NotBlank
    private boolean isActive;
}
