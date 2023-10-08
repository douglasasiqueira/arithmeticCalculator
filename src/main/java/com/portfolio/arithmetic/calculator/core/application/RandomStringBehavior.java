package com.portfolio.arithmetic.calculator.core.application;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.infrastructure.client.RandomClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RandomStringBehavior extends OperationBehavior{
    @Override
    public OperationType getOperationType(){
        return OperationType.RANDOM_STRING;
    };

    @Autowired
    private RandomClient randomClient;

    private List<BigDecimal> validateOperators(Map<String, String> operators) {
        return super.validateMathOperators(operators);
    };

    private Map<String, String> generateResult(List<BigDecimal> operators) {
        final Map<String, String> result = new HashMap<>();
        result.put("result", randomClient.getStrings().replace('\n', ','));
        return result;
    }

    public final Map<String, String> applyOperation(Map<String, String> operators) {
        List<BigDecimal> sanitizedOperators = this.validateOperators(operators);
        return this.generateResult(sanitizedOperators);
    }
}
