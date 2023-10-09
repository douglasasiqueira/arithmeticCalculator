package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MultiplicationBehaviorTest {
    private final MultiplicationBehavior multiplicationBehavior = new MultiplicationBehavior();

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(multiplicationBehavior.getOperationType(), OperationType.MULTIPLICATION);
    };

    @Test
    public void shouldRaiseIfInvalidOperators() {
        final Map<String, String> operators = new HashMap<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> multiplicationBehavior.applyBehavior(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> multiplicationBehavior.applyBehavior(operators));

        operators.put("value", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () -> multiplicationBehavior.applyBehavior(operators));

        operators.put("numbers", "1,test");

        Assertions.assertThrows(IllegalArgumentException.class, () -> multiplicationBehavior.applyBehavior(operators));
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        operators.put("numbers", "1,2,3");

        Assertions.assertEquals("6.000", multiplicationBehavior.applyBehavior(operators).get("result"));

        operators.put("numbers", "250");

        Assertions.assertEquals("250.0", multiplicationBehavior.applyBehavior(operators).get("result"));
    }
}
