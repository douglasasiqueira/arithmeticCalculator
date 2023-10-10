package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    public UserService userService;

    @Test
    public void shouldThrowIfUserAlreadyExists() {
        final User user = mockUser();

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(user));
    }

    @Test
    public void shouldCorrectlySaveUser() {
        final User user = mockUser();

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.empty());

        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldCorrectlyFindUserByUsername() {
        final User user = mockUser();

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        userService.getUser(user.getUsername());

        verify(userRepository, times(1))
                .findByUsername(user.getUsername());
    }

    @Test
    public void shouldCorrectlyFindUserById() {
        final User user = mockUser();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        userService.getUser(user.getId());

        verify(userRepository, times(1))
                .findById(user.getId());
    }

    private User mockUser() {
        User user = new User();

        user.setId(10L);
        user.setUsername("username");
        user.setPassword("password");

        return user;
    }
}
