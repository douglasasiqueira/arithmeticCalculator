package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.customException.ResourceNotFoundException;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.Record;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.infrastructure.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    private final AuthService authService;

    public void createRecord(User user, Operation operation, String operation_response) {
        final Record record = new Record();

        record.setAmount(operation.getCost());
        record.setOperation(operation);
        record.setOperationResponse(operation_response);
        record.setUser(user);
        record.setAmount(operation.getCost());
        record.setUserBalance(user.getBalance());

        recordRepository.save(record);
    }

    public List<Record> findAllByUserIdAndOperationIdPageable(final Long operationId, final int page, final int size) {
        final User user = authService.getAuthenticatedUser();
        return recordRepository.findAllByUserIdAndOperationIdPageableAndOperationIdPageable(user.getId(), operationId, PageRequest.of(page, size));
    }

    public Long countAllByUserIdAndOperationId(final Long operationId) {
        final User user = authService.getAuthenticatedUser();
        return recordRepository.countByUserIdAndOperationId(user.getId(), operationId);
    }

    public void deleteRecordId(final Long recordId) {
        final User user = authService.getAuthenticatedUser();
        final Record record = recordRepository.findByIdAndUserId(recordId, user.getId());

        if (Objects.isNull(record)) {
            throw new ResourceNotFoundException();
        }

        recordRepository.delete(record);
    }
}
