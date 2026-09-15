package com.rubentc.acmparcel.parcel.controller;

import com.rubentc.acmparcel.parcel.service.ParcelService;
import com.rubentc.acmparcel.parcel.dto.ParcelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parcel")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping
    @PreAuthorize("hasAuthority('parcel:read')")
    public List<ParcelResponse> getAllParcel() {
        return parcelService.getAllParcel();
    }
}
