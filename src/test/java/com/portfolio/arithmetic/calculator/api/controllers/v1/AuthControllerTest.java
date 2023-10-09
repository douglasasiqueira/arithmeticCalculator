package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.AuthRequestDTO;
import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.service.AuthService;
import com.portfolio.arithmetic.calculator.core.service.AuthServiceTest;
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

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService authRequestDTOService;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnClientErrorDueToValidations() throws Exception{
        final AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("invalidEmail");
        authRequestDTO.setPassword("password");

        mockMvc.perform(post("/v1/auth")).andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(authRequestDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnauthorized() throws Exception{
        final AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("valid@email.com");
        authRequestDTO.setPassword("password");

        when(authService.generateToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()))
                .thenThrow(new AuthenticationException(""));

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(authRequestDTO))
                        .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnSuccess() throws Exception{
        final AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setUsername("valid@email.com");
        authRequestDTO.setPassword("password");

        when(authService.generateToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()))
                .thenReturn("TOKEN VALUE");

        mockMvc.perform(post("/v1/auth")
                        .content(objectMapper.writeValueAsString(authRequestDTO))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("TOKEN VALUE"));
    }

}
