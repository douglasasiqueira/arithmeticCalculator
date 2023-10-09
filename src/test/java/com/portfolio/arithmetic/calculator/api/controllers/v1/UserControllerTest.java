package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.UserDTO;
import com.portfolio.arithmetic.calculator.api.mapper.UserMapper;
import com.portfolio.arithmetic.calculator.configuration.security.filter.AuthenticationFilterJWT;
import com.portfolio.arithmetic.calculator.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private AuthenticationFilterJWT authenticationFilterJWT;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnErrorIfInvalidPayload() throws Exception {
        final UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123");
        userDTO.setUsername("invalid");

        mockMvc.perform(post("/v1/user"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());

        userDTO.setUsername("mail@mail.com");

        mockMvc.perform(post("/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());

        userDTO.setPassword("1234567");

        mockMvc.perform(post("/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());

        verify(userService, times(1))
                .createUser(userMapper.userDTOToUser(userDTO));
    }

    @Test
    public void shouldReturnErrorIfUsernameAlreadyTaken() throws Exception {
        final UserDTO userDTO = new UserDTO();

        userDTO.setPassword("123");
        userDTO.setUsername("invalid");
        userDTO.setUsername("mail@mail.com");
        userDTO.setPassword("1234567");

        when(userService.createUser(any())).thenThrow(new IllegalArgumentException("Username already taken."));

        mockMvc.perform(post("/v1/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"Username already taken.\"}"));
    }

}
