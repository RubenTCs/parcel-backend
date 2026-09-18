package com.rubentc.acmparcel.parcel.dto;

import com.rubentc.acmparcel.customer.Customer;

public record CreateParcelRequest(
        String description,
        Float weight,
        Float height,
        Float width,
        Float length,
        Customer sender,
        Customer receiver
) {
}
