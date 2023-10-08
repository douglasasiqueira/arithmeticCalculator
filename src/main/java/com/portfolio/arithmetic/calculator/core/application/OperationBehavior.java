package com.portfolio.arithmetic.calculator.core.application;

import com.portfolio.arithmetic.calculator.core.customException.InvalidOperatorsException;
import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class OperationBehavior {
    public abstract OperationType getOperationType();

    public abstract Map<String, String> applyOperation(Map<String, String> operators);

    public final Map<String, String> apply(Map<String, String> operators) {
        try {
            return this.applyOperation(operators);
        } catch (IllegalArgumentException e){
            log.error(e.getMessage(), e);
            throw new InvalidOperatorsException();
        }
    }

    public List<BigDecimal> validateMathOperators(final Map<String, String> operators){
        String numbers;

        if (!operators.containsKey("numbers")) {
            throw new IllegalArgumentException("Numeric operators must have numbers key.");
        }

        numbers = operators.get("numbers");

        try {
            return Arrays.stream(numbers.split(","))
                    .map(Double::valueOf)
                    .map(BigDecimal::valueOf)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Numbers must have only float values.");
        }
    }
}
