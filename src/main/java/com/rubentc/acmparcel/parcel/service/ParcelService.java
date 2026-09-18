package com.rubentc.acmparcel.parcel.service;

import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.parcel.dto.CreateParcelRequest;
import com.rubentc.acmparcel.parcel.dto.ParcelResponse;
import com.rubentc.acmparcel.parcel.entity.Parcel;
import com.rubentc.acmparcel.parcel.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public ParcelResponse getParcelById(UUID parcelId) {

        Parcel parcel = parcelRepository.findById(parcelId)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel Id not found"));

        return new ParcelResponse(
                parcel.getId(),
                parcel.getDescription(),
                parcel.getSender(),
                parcel.getReceiver(),
                parcel.getWeight(),
                parcel.getLength(),
                parcel.getWidth(),
                parcel.getHeight()
        );
    }

    @Transactional
    public void createParcel(CreateParcelRequest request) {
        Parcel parcel = Parcel.builder()
                .description(request.description())
                .length(request.length())
                .width(request.width())
                .height(request.height())
                .build();

        parcelRepository.save(parcel);

        // Maybe it needs return
    }
}
