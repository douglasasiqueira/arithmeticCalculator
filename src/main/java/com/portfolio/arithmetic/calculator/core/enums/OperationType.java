package com.portfolio.arithmetic.calculator.core.enums;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public enum OperationType {
    ADDITION((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::add).toString()),
    SUBTRACTION((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::subtract).toString()),
    MULTIPLICATION((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::multiply).toString()),
    DIVISION((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::divide).toString()),
    SQUARE_ROOT((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::divide).toString()),
    RANDOM_STRING((List<BigDecimal> operators) -> operators.stream().reduce(BigDecimal.ZERO, BigDecimal::add).toString());

    OperationType(Function<List<BigDecimal>, String> apply) {
        this.apply = apply;
    }

    public final Function<List<BigDecimal>, String> apply;
}
