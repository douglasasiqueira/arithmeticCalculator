package com.portfolio.arithmetic.calculator.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

    @Email
    private String username;

    @NotEmpty
    private String password;
}
