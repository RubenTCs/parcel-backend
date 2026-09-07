package com.rubentc.acmparcel.user.service;

import com.rubentc.acmparcel.user.entity.User;
import com.rubentc.acmparcel.common.exception.CustomException;
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
                .orElseThrow(() -> new CustomException("Customer with Id" + userId +"did not found"));
    }
}
