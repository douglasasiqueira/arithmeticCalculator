package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SquareRootBehavior extends OperationBehavior {
    @Override
    public OperationType getOperationType(){
        return OperationType.SQUARE_ROOT;
    };

    private List<BigDecimal> validateOperators(Map<String, String> operators) {
        return super.validateMathOperators(operators);
    };

    private Map<String, String> generateResult(List<BigDecimal> operators) {
        final Map<String, String> result = new HashMap<>();
        final MathContext mc = new MathContext(10);
        final String resultValue = operators.stream().map(v -> v.sqrt(mc)).toList().toString();

        result.put("result", resultValue.substring(1, resultValue.length() - 1));

        return result;
    }

    public final Map<String, String> applyBehavior(Map<String, String> operators) {
        List<BigDecimal> sanitizedOperators = this.validateOperators(operators);
        return this.generateResult(sanitizedOperators);
    }
}
