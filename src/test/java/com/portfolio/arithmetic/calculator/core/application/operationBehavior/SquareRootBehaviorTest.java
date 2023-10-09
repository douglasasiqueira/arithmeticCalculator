package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SquareRootBehaviorTest {
    private final SquareRootBehavior squareRootBehavior = new SquareRootBehavior();

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(squareRootBehavior.getOperationType(), OperationType.SQUARE_ROOT);
    };

    @Test
    public void shouldRaiseIfInvalidOperators() {
        final Map<String, String> operators = new HashMap<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> squareRootBehavior.applyBehavior(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> squareRootBehavior.applyBehavior(operators));

        operators.put("value", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () -> squareRootBehavior.applyBehavior(operators));

        operators.put("numbers", "1,test");

        Assertions.assertThrows(IllegalArgumentException.class, () -> squareRootBehavior.applyBehavior(operators));
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        operators.put("numbers", "16,25,81");

        Assertions.assertEquals("4, 5, 9", squareRootBehavior.applyBehavior(operators).get("result"));

        operators.put("numbers", "100");

        Assertions.assertEquals("10", squareRootBehavior.applyBehavior(operators).get("result"));
    }
}