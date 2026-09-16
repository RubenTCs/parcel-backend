package com.rubentc.acmparcel.permission.controller;

import com.rubentc.acmparcel.permission.service.PermissionService;
import com.rubentc.acmparcel.permission.dto.PermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('permission:read')")
    @ResponseStatus(HttpStatus.OK)
    public List<PermissionResponse> getAllPermissions() {
        return permissionService.getAllPermission();
    }

    @GetMapping("/{permissionId}")
    @PreAuthorize("hasAuthority('permission:read')")
    @ResponseStatus(HttpStatus.OK)
    public PermissionResponse getPermissionById(@PathVariable UUID permissionId) {
        return permissionService.getPermissionById(permissionId);
    }
}
