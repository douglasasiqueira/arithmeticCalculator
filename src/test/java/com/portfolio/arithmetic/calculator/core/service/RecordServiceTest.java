package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.infrastructure.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTest {
    @Mock
    private AuthService authService;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    @Test
    public void shouldSaveRecord() {
        final User user = mockAuthenticatedUser();
        final Operation operation = mockOperation();
        final String response = "response";
        final Record record = new Record();

        record.setAmount(operation.getCost());
        record.setOperation(operation);
        record.setOperationResponse(response);
        record.setUser(user);
        record.setAmount(operation.getCost());
        record.setUserBalance(user.getBalance());

        recordService.createRecord(user, operation, response);

        verify(recordRepository, times(1)).save(record);
    }

    @Test
    public void shouldRetrieveRecords() {
        final User user = mockAuthenticatedUser();

        when(authService.getAuthenticatedUser()).thenReturn(user);

        recordService.findAllByUserIdAndOperationIdPageable(42L, 1, 10);

        verify(recordRepository, times(1))
                .findAllByUserIdAndOperationIdPageableAndOperationIdPageable(user.getId(), 42L, PageRequest.of(1, 10));
    }

    private Operation mockOperation() {
        Operation operation = new Operation();

        operation.setId(123);
        operation.setType(OperationType.ADDITION);
        operation.setCost(BigDecimal.ONE);

        return operation;
    }

    private User mockAuthenticatedUser() {
        User user = new User();

        user.setId(123L);
        user.setBalance(BigDecimal.TEN);

        return user;
    }
}
