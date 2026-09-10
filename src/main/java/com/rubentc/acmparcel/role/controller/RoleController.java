package com.rubentc.acmparcel.role.controller;

import com.rubentc.acmparcel.role.dto.request.CreateRoleRequest;
import com.rubentc.acmparcel.role.dto.request.UpdateRolePermissionRequest;
import com.rubentc.acmparcel.role.dto.response.CreateRoleResponse;
import com.rubentc.acmparcel.role.dto.response.RoleResponse;
import com.rubentc.acmparcel.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    public final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResponse> getAllRole(){
        return roleService.getAllRoles();
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('role:read')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse getRole(@PathVariable UUID roleId){
        return roleService.getRoleById(roleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRoleResponse createRole(
            @Valid @RequestBody CreateRoleRequest request){
        return roleService.createRole(request);
    }

    @PutMapping("/{roleId}/permissions")
    @PreAuthorize("hasAuthority('role:assign-permission')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRolePermission(
            @PathVariable UUID roleId,
            @Valid @RequestBody UpdateRolePermissionRequest request){

        roleService.updateRolePermission(roleId, request.permissionsIds());
    }

    // TODO: DELETE /{roleId} = Delete Role | role:delete
}
