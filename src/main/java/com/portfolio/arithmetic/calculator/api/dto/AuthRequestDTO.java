package com.portfolio.arithmetic.calculator.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDTO {
    @Email
    private String username;

    @NotNull
    private String password;
}
