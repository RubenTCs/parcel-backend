package com.rubentc.acmparcel.common.exception.dto;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp,
        String path
) {
}
