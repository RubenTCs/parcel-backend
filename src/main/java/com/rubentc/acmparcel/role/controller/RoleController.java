package com.rubentc.acmparcel.role.controller;

import com.rubentc.acmparcel.role.dto.RoleResponse;
import com.rubentc.acmparcel.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    public final RoleService roleService;

    // TODO: GET / = List roles | role:read
    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResponse> getAllRole(){
        return roleService.getAllRoles();
    }

    // TODO: GET /{rolesId} = Get Roles details | role:read
    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponse getRole(UUID roleId){
        return roleService.getRoleById(roleId);
    }
    // TODO: POST / = Crate Roles | role:create
    // TODO: PUT /{rolesId} = Update Role | role:update
    // TODO: DELETE /{roleId} = Delete Role | role:delete
}
