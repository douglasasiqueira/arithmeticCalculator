package com.portfolio.arithmetic.calculator.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @Email
    private String username;

    @NotEmpty
    @Size(min = 6)
    private String password;
}
