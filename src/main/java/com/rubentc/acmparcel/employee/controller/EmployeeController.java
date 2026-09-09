package com.rubentc.acmparcel.employee.controller;

import com.rubentc.acmparcel.employee.dto.request.CreateEmployeeAccountInvitationRequest;
import com.rubentc.acmparcel.employee.dto.request.UpdateEmployeeRolesRequest;
import com.rubentc.acmparcel.employee.dto.request.UpdateEmployeeStatusRequest;
import com.rubentc.acmparcel.employee.dto.response.CreateEmployeeAccountInvitationResponse;
import com.rubentc.acmparcel.employee.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('employee:read')")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('employee:read')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse getEmployeeById(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PreAuthorize("hasAuthority('employee:create')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployeeAccountInvitationResponse createEmployeeAccountInvitation(
            @Valid @RequestBody CreateEmployeeAccountInvitationRequest request) {

        return employeeService.createEmployeeAccountInvitation(request);
    }

    @PreAuthorize("hasAuthority('employee:assign-role')")
    @PutMapping("/{employeeId}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployeeRoles(@PathVariable UUID employeeId,
            @Valid @RequestBody UpdateEmployeeRolesRequest request
    ) {
        employeeService.updateEmployeeRoles(employeeId, request.roleIds() );
    }

    @PreAuthorize("hasAuthority('employee:update')")
    @PutMapping("/{employeeId}/status")
    public void updateEmployeeStatus(@PathVariable UUID employeeId,
            @Valid @RequestBody UpdateEmployeeStatusRequest request
    ){
        employeeService.updateEmployeeStatus(
                employeeId,
                request.status()
        );
    }

}
