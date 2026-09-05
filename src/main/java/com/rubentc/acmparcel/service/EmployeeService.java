package com.rubentc.acmparcel.service;

import com.rubentc.acmparcel.dto.request.CreateEmployeeRequest;
import com.rubentc.acmparcel.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.entity.Role;
import com.rubentc.acmparcel.entity.User;
import com.rubentc.acmparcel.enums.AccountStatus;
import com.rubentc.acmparcel.exception.CustomException;
import com.rubentc.acmparcel.repository.EmployeeRepository;
import com.rubentc.acmparcel.entity.Employee;
import com.rubentc.acmparcel.repository.RoleRepository;
import com.rubentc.acmparcel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    //This will be managed by HR or Owner
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new CustomException("User with email " + request.email() + " already exists");
        }

        User user = User.builder()
                .email(request.email())
                .status(AccountStatus.PENDING)
                .build();

        userRepository.save(user);

        List<Role> roleList = roleRepository.findAllById(request.roleIds());

        if(roleList.size() != request.roleIds().size()) {
            throw new CustomException("Role Id not found");
        }

        Set<Role> roles = new HashSet<>(roleList);

        Employee employee = Employee.builder()
                .user(user)
                .name(request.name())
                .roles(roles)
                .build();

        employeeRepository.save(employee);

        return EmployeeResponse.from(employee);
    }

    @Transactional
    public void updateEmployeeRoles(UUID employeeId, Set<UUID> roleIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee Id not found"));

        List<Role> roles = roleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {
            throw new CustomException("Role Id not found");
        }

        employee.setRoles(new HashSet<>(roles));
    }

    @Transactional
    public void updateEmployeeStatus(UUID employeeId, AccountStatus status) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new CustomException("Employee Id not found"));

        employee.getUser().setStatus(status);
    }
}
