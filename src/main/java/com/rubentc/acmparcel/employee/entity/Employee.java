package com.rubentc.acmparcel.employee.entity;

import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees", indexes = @Index(name = "email_index", columnList = "email"))
public class Employee {

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
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employeeRoles",
            joinColumns = @JoinColumn(name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

}
