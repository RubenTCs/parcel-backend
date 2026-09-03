package com.rubentc.jrtparcel.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "employees", indexes = @Index(name = "email_index", columnList = "email"))
public class Employee {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String employeeName;

    @Column(nullable = false)
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employeeRoles",
            joinColumns = @JoinColumn(name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();
}
