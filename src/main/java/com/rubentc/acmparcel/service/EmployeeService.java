package com.rubentc.acmparcel.service;

import com.rubentc.acmparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeActiveStatusRequest;
import com.rubentc.acmparcel.dto.request.UpdateEmployeeRoleRequest;
import com.rubentc.acmparcel.entity.Role;
import com.rubentc.acmparcel.exception.CustomException;
import com.rubentc.acmparcel.repository.EmployeeRepository;
import com.rubentc.acmparcel.entity.Employee;
import com.rubentc.acmparcel.repository.RoleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository , PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee createEmployee(CreateEmployeeRequest request) {

        if(employeeRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Employee with email " + request.getEmail() + " already exists");
        }

        Role employeeRole = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() ->
                        new CustomException("EMPLOYEE Role not found"));

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());

        employee.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        employee.setRoles(Set.of(employeeRole));

        return employeeRepository.save(employee);

    }

    @Transactional
    public Employee updateEmployeeRoles(UUID employeeId, UpdateEmployeeRoleRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new CustomException("Employee not found"));

        Set<Role> roles = new HashSet<>(
                roleRepository.findAllById(request.getRoleIds())
        );

        if (roles.size() != request.getRoleIds().size()) {
            throw new CustomException("One or more roles not found");
        }

        employee.setRoles(roles);

        return employeeRepository.save(employee);

    }

    @Transactional
    public Employee updateEmployeeActiveStatus(UUID id, @Valid UpdateEmployeeActiveStatusRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new CustomException("Employee not found"));

        employee.setActive(request.isActive());

        return employeeRepository.save(employee);

    }
}
