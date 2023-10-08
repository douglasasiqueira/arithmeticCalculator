package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public long createUser(final User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setBalance(BigDecimal.valueOf(1000L));
        return userRepository.save(user).getId();
    }

    public Optional<User> getUser(final String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }
}
