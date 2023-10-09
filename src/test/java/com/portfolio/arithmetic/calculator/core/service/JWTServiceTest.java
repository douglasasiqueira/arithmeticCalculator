package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {
    @InjectMocks
    private JWTService jwtService;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(jwtService, "secret", "secret");
        ReflectionTestUtils.setField(jwtService, "issuer", "issuer");
    }

    @Test
    public void shouldGenerateTokenCorrectly() {
        final User user = new User();

        user.setId(789L);

        final String token = jwtService.generateToken(user);
        final Optional<Long> userID = jwtService.getUserId(token);

        Assertions.assertTrue(userID.isPresent());
        Assertions.assertEquals(user.getId(), userID.get());
    }

    @Test
    public void shouldNotReturnUserIdIfInvalidToken() {
        final User user = new User();

        user.setId(789L);

        final String token = jwtService.generateToken(user);

        ReflectionTestUtils.setField(jwtService, "issuer", "invalid");

        final Optional<Long> userID = jwtService.getUserId(token);

        Assertions.assertTrue(userID.isEmpty());
    }
}
