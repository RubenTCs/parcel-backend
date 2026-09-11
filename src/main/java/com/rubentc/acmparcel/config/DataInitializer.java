package com.rubentc.acmparcel.config;

import com.rubentc.acmparcel.employee.entity.Employee;
import com.rubentc.acmparcel.employee.repository.EmployeeRepository;
import com.rubentc.acmparcel.permission.entity.Permission;
import com.rubentc.acmparcel.permission.repository.PermissionRepository;
import com.rubentc.acmparcel.role.entity.Role;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initializeData() {
        return args -> {


//==========================================================================
// region Permissions
//==========================================================================


            Permission employeeRead =
                    createPermission("employee:read");

            Permission employeeCreate =
                    createPermission("employee:create");

            Permission employeeUpdate =
                    createPermission("employee:update");

            Permission employeeDelete =
                    createPermission("employee:delete");

            Permission employeeAssignRole =
                    createPermission("employee:assign-role");

            Permission customerRead =
                    createPermission("customer:read");

            Permission customerCreate =
                    createPermission("customer:create");

            Permission customerUpdate =
                    createPermission("customer:update");

            Permission customerDelete =
                    createPermission("customer:delete");

            Permission parcelRead =
                    createPermission("parcel:read");

            Permission parcelCreate =
                    createPermission("parcel:create");

            Permission parcelUpdate =
                    createPermission("parcel:update");

            Permission parcelDelete =
                    createPermission("parcel:delete");

            Permission roleRead =
                    createPermission("role:read");

            Permission roleCreate =
                    createPermission("role:create");

            Permission roleUpdate =
                    createPermission("role:update");

            Permission roleDelete =
                    createPermission("role:delete");

            Permission roleAssignPermission =
                    createPermission("role:assign-permission");

            Permission permissionRead =
                    createPermission("permission:read");

            Permission permissionCreate =
                    createPermission("permission:create");

            Permission permissionUpdate =
                    createPermission("permission:update");

            Permission permissionDelete =
                    createPermission("permission:delete");

//==========================================================================
// endregion
//==========================================================================

//==========================================================================
// region Roles
//==========================================================================

            Role admin = createRole("ADMIN");

            Role staff = createRole("STAFF");

            Role customer = createRole("CUSTOMER");

//==========================================================================
// endregion
//==========================================================================

//==========================================================================
// region Admin Permissions
//==========================================================================

            admin.setPermissions(new HashSet<>(Set.of(
                    employeeRead,
                    employeeCreate,
                    employeeUpdate,
                    employeeDelete,
                    employeeAssignRole,

                    customerRead,
                    customerCreate,
                    customerUpdate,
                    customerDelete,

                    parcelRead,
                    parcelCreate,
                    parcelUpdate,
                    parcelDelete,

                    roleRead,
                    roleCreate,
                    roleUpdate,
                    roleDelete,
                    roleAssignPermission,

                    permissionRead,
                    permissionCreate,
                    permissionUpdate,
                    permissionDelete
            )));
//==========================================================================
// endregion
//==========================================================================

//==========================================================================
// region Staff Permissions
//==========================================================================

            staff.setPermissions(new HashSet<>(Set.of(
                    employeeRead,

                    customerRead,
                    customerCreate,
                    customerUpdate,

                    parcelRead,
                    parcelCreate,
                    parcelUpdate
            )));

//==========================================================================
// endregion
//==========================================================================

//==========================================================================
// region Customer Permissions
//==========================================================================

            customer.setPermissions(new HashSet<>(Set.of(
                    parcelRead,
                    parcelCreate
            )));


            roleRepository.saveAll(
                    List.of(admin, staff, customer)
            );
//==========================================================================
// endregion
//==========================================================================

//==========================================================================
// region Admin User & Employee
//==========================================================================

            if (userRepository.findByEmail("admin@example.com").isEmpty()) {

                User adminUser = User.builder()
                        .email("admin@example.com")
                        .passwordHash(
                                passwordEncoder.encode("admin123")
                        )
                        .status(AccountStatus.ACTIVE)
                        .build();

                userRepository.save(adminUser);

                Employee adminEmployee = Employee.builder()
                        .user(adminUser)
                        .name("System Administrator")
                        .roles(new HashSet<>(Set.of(admin)))
                        .build();

                employeeRepository.save(adminEmployee);
            }
//==========================================================================
// endregion Admin
//==========================================================================
        };
    }


    private Permission createPermission(String name) {

        return permissionRepository
                .findByName(name)
                .orElseGet(() ->
                        permissionRepository.save(
                                Permission.builder()
                                        .name(name)
                                        .build()
                        )
                );
    }


    private Role createRole(String name) {

        return roleRepository
                .findByName(name)
                .orElseGet(() ->
                        roleRepository.save(
                                Role.builder()
                                        .name(name)
                                        .permissions(new HashSet<>())
                                        .build()
                        )
                );
    }
}