package com.rubentc.acmparcel.parcel.service;

import com.rubentc.acmparcel.parcel.dto.ParcelResponse;
import com.rubentc.acmparcel.parcel.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;

    @Transactional(readOnly = true)
    public List<ParcelResponse> getAllParcel() {

       return parcelRepository.findAll()
               .stream()
               .map(parcel -> new ParcelResponse(
                       parcel.getId(),
                       parcel.getDescription(),
                       parcel.getSender(),
                       parcel.getReceiver(),
                       parcel.getWeight(),
                       parcel.getLength(),
                       parcel.getWidth(),
                       parcel.getHeight()
                       ))
               .toList();
    }
}
