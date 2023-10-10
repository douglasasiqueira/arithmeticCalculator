package com.portfolio.arithmetic.calculator.configuration.security;

import com.portfolio.arithmetic.calculator.configuration.security.filter.AuthenticationFilterJWT;
import com.portfolio.arithmetic.calculator.configuration.security.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationFilterJWT authFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST,"v1/user").permitAll()
                        .requestMatchers(HttpMethod.POST,"v1/auth").permitAll()
                        .requestMatchers(HttpMethod.GET,"v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET,"v3/api-docs/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"swagger-ui/*").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
