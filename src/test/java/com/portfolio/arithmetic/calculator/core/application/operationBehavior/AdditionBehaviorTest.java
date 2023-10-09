package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AdditionBehaviorTest{
    private final AdditionBehavior additionBehavior = new AdditionBehavior();

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(additionBehavior.getOperationType(), OperationType.ADDITION);
    };

    @Test
    public void shouldRaiseIfInvalidOperators() {
        final Map<String, String> operators = new HashMap<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> additionBehavior.applyBehavior(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> additionBehavior.applyBehavior(operators));

        operators.put("value", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () -> additionBehavior.applyBehavior(operators));

        operators.put("numbers", "1,test");

        Assertions.assertThrows(IllegalArgumentException.class, () -> additionBehavior.applyBehavior(operators));
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        operators.put("numbers", "1,2,3");

        Assertions.assertEquals("6.0", additionBehavior.applyBehavior(operators).get("result"));

        operators.put("numbers", "100");

        Assertions.assertEquals("100.0", additionBehavior.applyBehavior(operators).get("result"));
    }
}
