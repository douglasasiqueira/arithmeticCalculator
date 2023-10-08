package com.portfolio.arithmetic.calculator.configuration.security.filter;

import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.service.JWTService;
import com.portfolio.arithmetic.calculator.core.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Configuration
@Slf4j
public class AuthenticationFilterJWT extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final Optional<Long> optionalUserId = jwtService.getUserId(authorizationHeader);

        if (optionalUserId.isPresent()) {
            Optional<User> optionalUser = userService.getUser(optionalUserId.get());

            if (optionalUser.isPresent()) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                optionalUser.get().getId(),
                                null, optionalUser.get().getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
