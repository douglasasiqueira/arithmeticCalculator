package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.UserDTO;
import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.service.AuthService;
import com.portfolio.arithmetic.calculator.core.service.JWTService;
import com.portfolio.arithmetic.calculator.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnClientErrorDueToValidations() throws Exception{
        final UserDTO invalidUser = new UserDTO();
        invalidUser.setUsername("invalidEmail");
        invalidUser.setPassword("password");

        mockMvc.perform(post("/v1/auth")).andExpect(status().is4xxClientError());

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(invalidUser))
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnUnauthorized() throws Exception{
        final UserDTO user = new UserDTO();
        user.setUsername("valid@email.com");
        user.setPassword("password");

        when(authService.generateToken(user.getUsername(), user.getPassword()))
                .thenThrow(new AuthenticationException(""));

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnSuccess() throws Exception{
        final UserDTO user = new UserDTO();
        user.setUsername("valid@email.com");
        user.setPassword("password");

        when(authService.generateToken(user.getUsername(), user.getPassword()))
                .thenReturn("TOKEN VALUE");

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("TOKEN VALUE"));
    }

}
