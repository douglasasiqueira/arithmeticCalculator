package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private JWTService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    public AuthService authService;

    @Test
    public void shouldThrowIfUserNotExists() {
        final String username = "username";

        when(userService.getUser(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthenticationException.class,
                () -> authService.generateToken(username, ""));
    }

    @Test
    public void shouldThrowIfInvalidPassword() {
        final String username = "username";
        final String password = "invalid";
        final User user = mockUser();

        when(userService.getUser(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        Assertions.assertThrows(AuthenticationException.class,
                () -> authService.generateToken(username, password));
    }

    @Test
    public void shouldReturnTokenCorrectly() {
        final User user = mockUser();

        when(userService.getUser(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("TOKEN");

        Assertions.assertEquals("TOKEN",
                authService.generateToken(user.getUsername(), user.getPassword()));
    }

    @Test
    public void shouldThrowIfTryToGetNotAuthenticatedUser() {
        Assertions.assertThrows(AuthenticationException.class,
                () -> authService.getAuthenticatedUser());
    }

    @Test
    public void shouldThrowIfUserNotFound() {
        final User user = mockUser();

        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(user.getId(), null, user.getAuthorities()));

        when(userService.getUser(user.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(AuthenticationException.class,
                () -> authService.getAuthenticatedUser());

        verify(userService, times(1)).getUser(user.getId());
    }

    @Test
    public void shouldReturnAuthenticatedUserCorrectly() {
        final User user = mockUser();

        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(user.getId(), null, user.getAuthorities()));

        when(userService.getUser(user.getId())).thenReturn(Optional.of(user));

        Assertions.assertEquals(authService.getAuthenticatedUser(), user);
    }

    private User mockUser() {
        User user = new User();

        user.setId(98L);
        user.setUsername("username");
        user.setPassword("password");

        return user;
    }
}
