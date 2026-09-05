package com.rubentc.acmparcel.controller;

import com.rubentc.acmparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeStatusRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeRolesRequest;
import com.rubentc.acmparcel.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.entity.Employee;
import com.rubentc.acmparcel.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    @PreAuthorize("hasAuthority('')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

//    @PreAuthorize("hasAuthority('')")
    @PutMapping("/{employeeId}/roles")
    public ResponseEntity<Void> updateEmployeeRoles(
            @PathVariable UUID employeeId,
            @Valid @RequestBody UpdateEmployeeRolesRequest request
    ) {
        employeeService.updateEmployeeRoles( employeeId, request.roleIds() );

        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("hasAuthority('')")
    @PatchMapping("/{employeeId}/status")
    public ResponseEntity<Void> updateEmployeeStatus(
            @PathVariable UUID employeeId,
            @Valid @RequestBody UpdateEmployeeStatusRequest request
    ){
        employeeService.updateEmployeeStatus(
                employeeId,
                request.status()
        );

        return ResponseEntity.noContent().build();
    }

}
