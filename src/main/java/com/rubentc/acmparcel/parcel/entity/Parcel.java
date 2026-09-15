package com.rubentc.acmparcel.parcel.entity;

import com.rubentc.acmparcel.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "parcels")
public class Parcel {
    @Id
    private UUID id;

    //Other important tracking fields like warehouses, maybe delivery order, etc

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "senderId", nullable = false)
    private Customer sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiverId", nullable = false)
    private Customer receiver;

    @NotNull
    private Float weight;
    private Float length;
    private Float height;
    private Float width;

    @NotBlank
    private String description;

}
