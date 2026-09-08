package com.rubentc.acmparcel.employee.service;

import com.rubentc.acmparcel.employee.dto.request.CreateEmployeeAccountRequest;
import com.rubentc.acmparcel.employee.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.common.exception.CustomException;
import com.rubentc.acmparcel.employee.repository.EmployeeRepository;
import com.rubentc.acmparcel.employee.entity.Employee;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import com.rubentc.acmparcel.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    //This will be managed by HR or Owner
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeAccountRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new CustomException("User with email " + request.email() + " already exists");
        }

        User user = User.builder()
                .email(request.email())
                .status(AccountStatus.PENDING)
                .passwordHash(null)
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
