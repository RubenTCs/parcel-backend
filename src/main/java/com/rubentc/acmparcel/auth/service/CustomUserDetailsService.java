package com.rubentc.acmparcel.auth.service;

import com.rubentc.acmparcel.auth.security.CustomUserDetails;
import com.rubentc.acmparcel.permission.entity.Permission;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorizationCacheService authorizationCacheService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // 1. Load the user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        // 2. Check Redis
        Set<String> authorities =
                authorizationCacheService
                        .getAuthorities(user.getId());

        // 3. Cache miss
        if (authorities.isEmpty()) {

            User userWithAuthorities =
                    userRepository
                            .findByEmailWithAuthorities(email)
                            .orElseThrow(() ->
                                    new UsernameNotFoundException(
                                            "User not found: " + email
                                    )
                            );

            authorities =
                    extractAuthorities(userWithAuthorities);

            // 4. Put authorities into Redis
            authorizationCacheService.cacheAuthorities(
                    user.getId(),
                    authorities
            );
        }

        // 5. Build UserDetails
        return new CustomUserDetails(
                user,
                authorities
        );
    }

    private Set<String> extractAuthorities(User user) {

        if (user.getEmployee() == null) {
            return Set.of();
        }

        return user.getEmployee()
                .getRoles()
                .stream()
                .flatMap(role ->
                        role.getPermissions().stream()
                )
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }

}