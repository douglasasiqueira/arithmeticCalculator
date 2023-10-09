package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.application.operationBehavior.MultiplicationBehavior;
import com.portfolio.arithmetic.calculator.core.application.operationBehavior.OperationBehavior;
import com.portfolio.arithmetic.calculator.core.customException.*;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.infrastructure.repository.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTest {
    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AuthService authService;

    @Mock
    private RecordService recordService;

    @Mock
    private List<OperationBehavior> operationBehaviorListMock;

    @InjectMocks
    private OperationService operationService;

    private final List<OperationBehavior> operationBehaviorList = new ArrayList<>();

    @Test
    public void shouldThrowIfUserNotAuthenticated() {
        when(authService.getAuthenticatedUser()).thenThrow(new AuthenticationException(""));

        Assertions.assertThrows(AuthenticationException.class,
                () -> operationService.executeOperation(1234, null));
    }

    @Test
    public void shouldThrowIfOperationNotExists() {
        when(operationRepository.findById(1234L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> operationService.executeOperation(1234, null));
    }

    @Test
    public void shouldThrowIfInvalidOperators() {
        final Operation operation = mockOperation(OperationType.ADDITION);
        final User user = mockAuthenticatedUser();
        final Map<String, String> operators = new HashMap<>();

        mockOperationBehavior();

        when(operationRepository.findById(1234L)).thenReturn(Optional.of(operation));
        when(authService.getAuthenticatedUser()).thenReturn(user);

        Assertions.assertThrows(ResourceNotImplementedException.class,
                () -> operationService.executeOperation(1234, operators));
    }

    @Test
    public void shouldThrowIfBehaviorNotFound() {
        final OperationBehavior operationBehavior = mockOperationBehavior();
        final Operation operation = mockOperation(operationBehavior.getOperationType());
        final User user = mockAuthenticatedUser();
        final Map<String, String> operators = new HashMap<>();

        mockOperationBehavior();

        when(operationRepository.findById(1234L)).thenReturn(Optional.of(operation));
        when(authService.getAuthenticatedUser()).thenReturn(user);

        Assertions.assertThrows(InvalidOperatorsException.class,
                () -> operationService.executeOperation(1234, operators));
    }

    @Test
    public void shouldThrowINotEnoughBalance() {
        final Operation operation = mockOperation(OperationType.ADDITION);
        final User user = mockAuthenticatedUser();
        user.setBalance(BigDecimal.ZERO);
        final Map<String, String> operators = new HashMap<>();

        when(operationRepository.findById(1234L)).thenReturn(Optional.of(operation));
        when(authService.getAuthenticatedUser()).thenReturn(user);

        Assertions.assertThrows(InsufficientBalanceException.class,
                () -> operationService.executeOperation(1234, operators));
    }

    @Test
    public void shouldWithDrawAndSaveRecordAndReturnOperation() {
        final OperationBehavior operationBehavior = mockOperationBehavior();
        final Operation operation = mockOperation(operationBehavior.getOperationType());
        final User user = mockAuthenticatedUser();
        final Map<String, String> operators = new HashMap<>();
        operators.put("numbers", "10,5");

        Map<String, String> actualResponse;
        Map<String, String> expectedResponse;

        expectedResponse = operationBehavior.apply(operators);

        when(operationRepository.findById(1234L)).thenReturn(Optional.of(operation));
        when(authService.getAuthenticatedUser()).thenReturn(user);

        Assertions.assertEquals(BigDecimal.TEN, user.getBalance());

        actualResponse = operationService.executeOperation(1234, operators);

        Assertions.assertEquals(expectedResponse, actualResponse);
        Assertions.assertEquals(BigDecimal.valueOf(9L), user.getBalance());
        verify(recordService, times(1))
                .createRecord(user, operation, actualResponse.toString());
    }

    private Operation mockOperation(final OperationType operationType) {
        Operation operation = new Operation();

        operation.setId(123);
        operation.setType(operationType);
        operation.setCost(BigDecimal.ONE);

        return operation;
    }

    private User mockAuthenticatedUser() {
        User user = new User();

        user.setId(123L);
        user.setBalance(BigDecimal.TEN);

        return user;
    }

    private OperationBehavior mockOperationBehavior() {
        final OperationBehavior operationBehavior = new MultiplicationBehavior();

        operationBehaviorList.add(operationBehavior);

        when(operationBehaviorListMock.stream()).thenReturn(operationBehaviorList.stream());

        return operationBehavior;
    }
}
