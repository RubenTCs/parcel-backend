package com.rubentc.acmparcel.employee.service;

import com.rubentc.acmparcel.auth.service.InvitationService;
import com.rubentc.acmparcel.common.exception.AlreadyExistException;
import com.rubentc.acmparcel.employee.dto.request.CreateEmployeeAccountByInvitationRequest;
import com.rubentc.acmparcel.employee.dto.response.CreateEmployeeAccountByInvitationResponse;
import com.rubentc.acmparcel.employee.dto.response.EmployeeResponse;
import com.rubentc.acmparcel.employee.dto.response.EmployeeRoleResponse;
import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.employee.repository.EmployeeRepository;
import com.rubentc.acmparcel.employee.entity.Employee;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final InvitationService invitationService;

    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employee -> new EmployeeResponse(
                        employee.getId(),
                        employee.getUser().getId(),
                        employee.getUser().getEmail(),
                        employee.getName(),
                        employee.getUser().getStatus(),
                        employee.getRoles()
                                .stream()
                                .map(role -> new EmployeeRoleResponse(
                                        role.getId(),
                                        role.getName()
                                ))
                                .collect(Collectors.toSet())
                ))
                .toList();


    }

    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(UUID id) {

        Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee Id not found"));

        return new EmployeeResponse(
                employee.getId(),
                employee.getUser().getId(),
                employee.getUser().getEmail(),
                employee.getName(),
                employee.getUser().getStatus(),
                employee.getRoles()
                        .stream()
                        .map(role -> new EmployeeRoleResponse(
                                role.getId(),
                                role.getName()
                        ))
                        .collect(Collectors.toSet())
        );
    }

    @Transactional
    public CreateEmployeeAccountByInvitationResponse createEmployeeAccountByInvitation(CreateEmployeeAccountByInvitationRequest request) {

        if(userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistException(request.email());
        }

        User user = User.builder()
                .email(request.email())
                .status(AccountStatus.PENDING)
                .passwordHash(null)
                .build();

        Set<Role> roles = new HashSet<>();

        if (request.roleIds() != null && !request.roleIds().isEmpty()) {

            List<Role> roleList = roleRepository.findAllById(request.roleIds());

            if (roleList.size() != request.roleIds().size()) {
                throw new ResourceNotFoundException("Role Id not found");
            }

            roles.addAll(roleList);
        }

        Employee employee = Employee.builder()
                .user(user)
                .name(request.name())
                .roles(roles)
                .build();

        userRepository.save(user);
        employeeRepository.save(employee);

        String invitationToken =
                invitationService.createInvitation(user);

        return new CreateEmployeeAccountByInvitationResponse(
                employee.getId(),
                invitationToken
        );
    }

    @Transactional
    public void updateEmployeeRoles(UUID employeeId, Set<UUID> roleIds) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id not found"));

        List<Role> roles = roleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {
            throw new ResourceNotFoundException("Role Id not found");
        }

        employee.setRoles(new HashSet<>(roles));
    }

    @Transactional
    public void updateEmployeeStatus(UUID employeeId, AccountStatus status) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Id not found"));

        employee.getUser().setStatus(status);
    }
}
