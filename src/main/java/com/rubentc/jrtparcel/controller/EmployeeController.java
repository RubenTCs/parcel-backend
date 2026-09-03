package com.rubentc.jrtparcel.controller;

import com.rubentc.jrtparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.jrtparcel.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public String createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

}
