package com.rubentc.acmparcel.user.service;

import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.common.exception.ResourceNotFoundException;
import com.rubentc.acmparcel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with Id" + userId +"did not found"));
    }
}
