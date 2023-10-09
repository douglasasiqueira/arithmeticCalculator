package com.portfolio.arithmetic.calculator.core.entity;

import com.portfolio.arithmetic.calculator.core.application.operationBehavior.OperationBehavior;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotImplementedException;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    private BigDecimal cost;

    public Map<String, String> getResponse(final List<OperationBehavior> behaviors,
                                           final Map<String, String> operators) {
        if (Objects.isNull(behaviors)) {
            throw new ResourceNotImplementedException();
        }

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
