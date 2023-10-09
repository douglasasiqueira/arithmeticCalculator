package com.portfolio.arithmetic.calculator.configuration.security.beans;

import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Configuration
public class SecurityBeans {
    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static Long getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication) &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return (Long) authentication.getPrincipal();
        }

        throw new AuthenticationException("Couldn't find logged User.");
    }
}
