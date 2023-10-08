package com.portfolio.arithmetic.calculator.core.application;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubtractionBehavior extends OperationBehavior{
    @Override
    public OperationType getOperationType(){
        return OperationType.SUBTRACTION;
    };

    private List<BigDecimal> validateOperators(Map<String, String> operators) {
        return super.validateMathOperators(operators);
    };

    private Map<String, String> generateResult(List<BigDecimal> operators) {
        final Map<String, String> result = new HashMap<>();
        result.put("result", operators.stream().reduce( BigDecimal::add).toString());
        return result;
    }

    public final Map<String, String> applyOperation(Map<String, String> operators) {
        List<BigDecimal> sanitizedOperators = this.validateOperators(operators);
        return this.generateResult(sanitizedOperators);
    }
}
