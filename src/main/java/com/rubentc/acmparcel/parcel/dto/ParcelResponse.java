package com.rubentc.acmparcel.parcel.dto;

import com.rubentc.acmparcel.customer.Customer;

import java.util.UUID;

public record ParcelResponse(
        UUID id,
        String description,
        Customer sender,
        Customer receiver,
        Float weight,
        Float length,
        Float height,
        Float width
) {
}
