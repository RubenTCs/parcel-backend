package com.rubentc.acmparcel.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

public class Customer {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "userId",
            nullable = false,
            unique = true
    )
    private User user;

    @Column(nullable = false)
    private String firstName;

    // yada yada other fields
}
