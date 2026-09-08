package com.rubentc.acmparcel.config;

import com.rubentc.acmparcel.user.entity.AccountStatus;
import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initializeUser(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByEmail("admin@example.com").isPresent()) {
                return;
            }

            User user = User.builder()
                    .email("admin@example.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .status(AccountStatus.ACTIVE)
                    .build();

            userRepository.save(user);

            //Seed Admin Role later
        };
    }
}
