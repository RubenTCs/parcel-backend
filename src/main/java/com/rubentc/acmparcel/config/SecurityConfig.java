package com.rubentc.acmparcel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/**").permitAll()
//
//                        .requestMatchers(HttpMethod.GET, "/api/employees/**")
//                        .hasAnyRole("", "ADMIN")
//
//                        .requestMatchers(HttpMethod.POST, "/api/employees/**")
//                        .hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.PUT, "/api/employees/**")
//                        .hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**")
//                        .hasRole("ADMIN")
//
//                        .anyRequest().authenticated()
//                );
//    }
}
