package com.rubentc.acmparcel.role.service;

import com.rubentc.acmparcel.common.exception.AlreadyExistException;
import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.permission.dto.PermissionResponse;
import com.rubentc.acmparcel.permission.entity.Permission;
import com.rubentc.acmparcel.permission.repository.PermissionRepository;
import com.rubentc.acmparcel.role.dto.request.CreateRoleRequest;
import com.rubentc.acmparcel.role.dto.response.CreateRoleResponse;
import com.rubentc.acmparcel.role.dto.response.RoleResponse;
import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

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

    @Transactional
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

    @Transactional
    public CreateRoleResponse createRole(CreateRoleRequest request) {

        if(roleRepository.existsByName(request.name())){
            throw new AlreadyExistException(request.name());
        }

        Set<Permission> permissions = new HashSet<>();

        if (request.permissionsId() != null && !request.permissionsId().isEmpty()) {

            List<Permission> permissionList = permissionRepository.findAllById(request.permissionsId());

            if (permissionList.size() != request.permissionsId().size()) {
                throw new ResourceNotFoundException("Permission Id not found");
            }

            permissions.addAll(permissionList);
        }

        Role role = Role.builder()
                .name(request.name())
                .description(request.description())
                .permissions(permissions)
                .build();

        roleRepository.saveAndFlush(role);

        Set<PermissionResponse> permissionResponses =
                role.getPermissions()
                        .stream()
                        .map(permission -> new PermissionResponse(
                                permission.getId(),
                                permission.getName(),
                                permission.getDescription()
                        ))
                        .collect(Collectors.toSet());


        return new CreateRoleResponse(
                role.getName(),
                role.getDescription(),
                permissionResponses,
                role.getCreatedAt()
        );
    }
}
