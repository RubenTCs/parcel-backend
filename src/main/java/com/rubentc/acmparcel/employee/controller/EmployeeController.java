package com.rubentc.acmparcel.employee.controller;

import com.rubentc.acmparcel.employee.dto.request.CreateEmployeeAccountInvitationRequest;
import com.rubentc.acmparcel.employee.dto.request.UpdateEmployeeStatusRequest;
import com.rubentc.acmparcel.employee.dto.request.UpdateEmployeeRolesRequest;
import com.rubentc.acmparcel.employee.dto.response.CreateEmployeeAccountInvitationResponse;
import com.rubentc.acmparcel.employee.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    @PreAuthorize("hasAuthority('')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse getAllEmployees() {
        return null; // To Test Postman connection, will update later
    }

//    @PreAuthorize("hasAuthority('')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateEmployeeAccountInvitationResponse> createEmployeeAccountInvitation(
            @Valid @RequestBody CreateEmployeeAccountInvitationRequest request) {

        CreateEmployeeAccountInvitationResponse response = employeeService.createEmployeeAccountInvitation(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
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
