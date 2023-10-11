package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.arithmetic.calculator.api.dto.OperationDTO;
import com.portfolio.arithmetic.calculator.api.mapper.OperationMapper;
import com.portfolio.arithmetic.calculator.configuration.security.filter.AuthenticationFilterJWT;
import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.customException.InsufficientBalanceException;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotFoundException;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.core.service.AuthServiceTest;
import com.portfolio.arithmetic.calculator.core.service.OperationService;
import com.portfolio.arithmetic.calculator.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OperationControllerTest {

    @MockBean
    private AuthenticationFilterJWT authenticationFilterJWT;

    @MockBean
    private AuthServiceTest authService;

    @MockBean
    private UserService userService;

    @MockBean
    private OperationService operationService;

    @MockBean
    private OperationMapper operationMapper;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnAllOperations() throws Exception{
        final List<Operation> operationList = operationList();
        final List<OperationDTO> operationDTOList = operationList.stream()
                .map(operationMapper::operationToOperationDTO).toList();

        when(operationService.getAll()).thenReturn(operationList);

        mockMvc.perform(get("/v1/operation"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(operationDTOList)));
    }

    @Test
    public void shouldReturnErrorDueToInvalidPayload() throws Exception{
        mockMvc.perform(post("/v1/operation/1234"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content(""))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorIfOperationNotExists() throws Exception{
        when(operationService.executeOperation(anyLong(), any()))
                .thenThrow(new ResourceNotFoundException());

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnErrorIfUserNotAuthenticated() throws Exception{
        when(operationService.executeOperation(anyLong(), any()))
                .thenThrow(new AuthenticationException(""));

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnErrorIfUserHasNotEnoughBalance() throws Exception{
        when(operationService.executeOperation(anyLong(), any()))
                .thenThrow(new InsufficientBalanceException(""));

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldReturnSuccess() throws Exception{
        final Map<String, String> response = new HashMap<>();
        response.put("result", "testResult");

        when(operationService.executeOperation(anyLong(), any()))
                .thenReturn(response);

        mockMvc.perform(post("/v1/operation/1234")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    private User authenticatedUser(final long balance) {
        final User user = new User();
        user.setId(98L);
        user.setBalance(BigDecimal.valueOf(balance));

        return user;
    }

    private List<Operation> operationList() {
        final Operation operation1 = new Operation();
        final Operation operation2 = new Operation();

        operation1.setId(1L);
        operation2.setId(2L);

        operation1.setType(OperationType.MULTIPLICATION);
        operation2.setType(OperationType.RANDOM_STRING);

        operation1.setCost(BigDecimal.TEN);
        operation2.setCost(BigDecimal.ONE);

        return List.of(operation1, operation2);
    }

}
