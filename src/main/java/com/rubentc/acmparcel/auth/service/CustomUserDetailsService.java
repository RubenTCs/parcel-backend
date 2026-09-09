package com.rubentc.acmparcel.auth.service;

import com.rubentc.acmparcel.auth.security.CustomUserDetails;
import com.rubentc.acmparcel.employee.entity.Employee;
import com.rubentc.acmparcel.employee.repository.EmployeeRepository;
import com.rubentc.acmparcel.role.repository.RoleRepository;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmailWithAuthorities(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        return new CustomUserDetails(user);

    }
}