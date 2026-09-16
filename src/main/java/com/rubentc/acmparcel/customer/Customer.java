package com.rubentc.acmparcel.customer;

import com.rubentc.acmparcel.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
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

    private String lastName;

    private String address; //TODO: Considering making new Address Entity

    // yada yada other fields
}
