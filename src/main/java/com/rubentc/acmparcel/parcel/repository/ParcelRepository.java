package com.rubentc.acmparcel.parcel.repository;

import com.rubentc.acmparcel.parcel.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, UUID> {
}
