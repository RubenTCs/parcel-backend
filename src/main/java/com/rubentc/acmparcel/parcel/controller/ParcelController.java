package com.rubentc.acmparcel.parcel.controller;

import com.rubentc.acmparcel.parcel.service.ParcelService;
import com.rubentc.acmparcel.parcel.dto.ParcelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping
    @PreAuthorize("hasAuthority('parcel:read')")
    @ResponseStatus(HttpStatus.OK)
    public List<ParcelResponse> getAllParcel() {
        return parcelService.getAllParcel();
    }

    @GetMapping("/{parcelId}")
    @PreAuthorize("hasAuthority('parcel:read')")
    @ResponseStatus(HttpStatus.OK)
    public ParcelResponse getParcelById(@PathVariable UUID parcelId) {
        return parcelService.getParcelById(parcelId);
    }
}
