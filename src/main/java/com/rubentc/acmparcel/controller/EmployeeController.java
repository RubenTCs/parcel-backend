package com.rubentc.acmparcel.controller;

import com.rubentc.acmparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeActiveStatusRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeRoleRequest;
import com.rubentc.acmparcel.entity.Employee;
import com.rubentc.acmparcel.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @PutMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployeeRole(@PathVariable UUID id, @Valid @RequestBody UpdateEmployeeRoleRequest request) {

        return employeeService.updateEmployeeRoles(id, request);
    }

    public Employee updateEmployeeActiveStatusRequest(@PathVariable UUID id, @Valid @RequestBody UpdateEmployeeActiveStatusRequest request) {
        return employeeService.updateEmployeeActiveStatus(id, request);
    }

}
