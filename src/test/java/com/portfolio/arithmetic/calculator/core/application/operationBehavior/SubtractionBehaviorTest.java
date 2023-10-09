package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SubtractionBehaviorTest {
    private final SubtractionBehavior subtractionBehavior = new SubtractionBehavior();

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(subtractionBehavior.getOperationType(), OperationType.SUBTRACTION);
    };

    @Test
    public void shouldRaiseIfInvalidOperators() {
        final Map<String, String> operators = new HashMap<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> subtractionBehavior.applyBehavior(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> subtractionBehavior.applyBehavior(operators));

        operators.put("value", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () -> subtractionBehavior.applyBehavior(operators));

        operators.put("numbers", "1,test");

        Assertions.assertThrows(IllegalArgumentException.class, () -> subtractionBehavior.applyBehavior(operators));
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        operators.put("numbers", "1,2,3");

        Assertions.assertEquals("-4.0", subtractionBehavior.applyBehavior(operators).get("result"));

        operators.put("numbers", "350");

        Assertions.assertEquals("350.0", subtractionBehavior.applyBehavior(operators).get("result"));
    }
}
