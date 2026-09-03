package com.rubentc.jrtparcel.service;

import com.rubentc.jrtparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.jrtparcel.exception.CustomException;
import com.rubentc.jrtparcel.repository.EmployeeRepository;
import com.rubentc.jrtparcel.entity.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee createEmployee(CreateEmployeeRequest request) {
        if(employeeRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Employee with email " + request.getEmail() + " already exists");
        }

        Employee employee = new Employee();
        employee.setEmail(request.getEmail());
        employee.setEmployeeName(request.getName());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        employee.setPassword(hashedPassword);

        return employeeRepository.save(employee);

    }
}
