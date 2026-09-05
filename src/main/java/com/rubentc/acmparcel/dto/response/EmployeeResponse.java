package com.rubentc.acmparcel.dto.response;

import com.rubentc.acmparcel.entity.Employee;
import com.rubentc.acmparcel.entity.Role;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record EmployeeResponse(
        UUID id,
        String email,
        String name,
        Set<String> roles
) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getUser().getEmail(),
                employee.getName(),
                employee.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
        );
    }
}
