package com.portfolio.arithmetic.calculator.core.service;

import com.portfolio.arithmetic.calculator.core.application.operationBehavior.OperationBehavior;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotFoundException;
import com.portfolio.arithmetic.calculator.core.entity.Operation;
import com.portfolio.arithmetic.calculator.core.entity.User;
import com.portfolio.arithmetic.calculator.infrastructure.repository.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private List<OperationBehavior> operationBehaviorList;

    public List<Operation> getAll() {
        return operationRepository.findAll();
    }

    @Transactional
    public Map<String, String> executeOperation(final long id, final Map<String, String> operators) {
        final Optional<Operation> operation = operationRepository.findById(id);
        final User user = authService.getAuthenticatedUser();
        Map<String, String> response;

        if (operation.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        response = operation.get().getResponse(this.operationBehaviorList, operators);
        user.withdraw(operation.get().getCost());
        recordService.createRecord(user, operation.get(), response.toString());

        return response;
    }
}
