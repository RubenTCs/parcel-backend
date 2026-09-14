package com.rubentc.acmparcel.permission.service;

import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.permission.dto.PermissionResponse;
import com.rubentc.acmparcel.permission.entity.Permission;
import com.rubentc.acmparcel.permission.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor()
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(UUID id){
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission Id not found"));

        return new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermission() {
        return permissionRepository.findAll()
                .stream()
                .map(permission -> new PermissionResponse(
                        permission.getId(),
                        permission.getName(),
                        permission.getDescription()
                )).toList();
    }
}
