package com.rubentc.acmparcel.role.service;

import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.permission.dto.PermissionResponse;
import com.rubentc.acmparcel.role.dto.RoleResponse;
import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getPermissions()
                                .stream()
                                .map(permission -> new PermissionResponse(
                                        permission.getId(),
                                        permission.getName(),
                                        permission.getDescription()
                                ))
                                .collect(Collectors.toSet())
                ))
                .toList();
    }

    public RoleResponse getRoleById(UUID id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role Id not found"));

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions()
                        .stream()
                        .map(permission -> new PermissionResponse(
                                permission.getId(),
                                permission.getName(),
                                permission.getDescription()
                        )).collect(Collectors.toSet())
        );
    }
}
