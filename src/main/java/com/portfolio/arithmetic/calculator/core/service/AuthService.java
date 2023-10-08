package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.configuration.security.beans.SecurityBeans;
import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    public String generateToken(final String username, final String password) {
        final Optional<User> optionalUser =  userService.getUser(username);

        System.out.println(username);

        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Couldn't Authenticate user");
        }

        if (!passwordEncoder.matches(password, optionalUser.get().getPassword())) {
            throw new AuthenticationException("Couldn't Authenticate user");
        }

        return jwtService.generateToken(optionalUser.get());
    }

    public User getAuthenticatedUser() {
        Long loggedUserId = SecurityBeans.getAuthenticatedUser();
        Optional<User> user = userService.getUser(loggedUserId);

        if (user.isEmpty()) {
            throw new AuthenticationException(
                    String.format("Authenticated user does not exists. User ID = %s", loggedUserId));
        }

        return user.get();
    }
}
