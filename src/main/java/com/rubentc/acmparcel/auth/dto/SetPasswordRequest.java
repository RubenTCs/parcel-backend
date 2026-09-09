package com.rubentc.acmparcel.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SetPasswordRequest(
        @NotBlank
        @Size(min = 8)
        String password,

        @NotBlank
        String confirmPassword,

        @NotBlank
        String token
) {
}
