package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class DivisionBehaviorTest {
    private final DivisionBehavior divisionBehavior = new DivisionBehavior();

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(divisionBehavior.getOperationType(), OperationType.DIVISION);
    };

    @Test
    public void shouldRaiseIfInvalidOperators() {
        final Map<String, String> operators = new HashMap<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionBehavior.applyBehavior(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionBehavior.applyBehavior(operators));

        operators.put("value", "value");

        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionBehavior.applyBehavior(operators));

        operators.put("numbers", "1,test");

        Assertions.assertThrows(IllegalArgumentException.class, () -> divisionBehavior.applyBehavior(operators));
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        operators.put("numbers", "3,2,1");

        Assertions.assertEquals("1.5000000000", divisionBehavior.applyBehavior(operators).get("result"));

        operators.put("numbers", "350");

        Assertions.assertEquals("350.0", divisionBehavior.applyBehavior(operators).get("result"));
    }
}
