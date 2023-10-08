package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.UserDTO;
import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.service.AuthService;
import com.portfolio.arithmetic.calculator.core.service.JWTService;
import com.portfolio.arithmetic.calculator.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OperationControllerTest {

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

}
