package com.portfolio.arithmetic.calculator.core.entity;

import com.portfolio.arithmetic.calculator.core.application.OperationBehavior;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotImplementedException;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Entity
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    private BigDecimal cost;

    public Map<String, String> apply(final List<OperationBehavior> behaviors,
                                      final Map<String, String> operators) {
        final Optional<OperationBehavior> optionalOperationBehavior =
                behaviors.stream()
                .filter(operationBehavior ->
                        operationBehavior.getOperationType().equals(this.getType()))
                .findFirst();

        if (optionalOperationBehavior.isEmpty()) {
            throw new ResourceNotImplementedException();
        }

        return optionalOperationBehavior.get().apply(operators);
    }
}
