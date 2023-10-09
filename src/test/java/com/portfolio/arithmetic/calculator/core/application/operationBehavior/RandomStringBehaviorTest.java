package com.portfolio.arithmetic.calculator.core.application.operationBehavior;

import com.portfolio.arithmetic.calculator.core.enums.OperationType;
import com.portfolio.arithmetic.calculator.infrastructure.client.RandomClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RandomStringBehaviorTest {

    @Mock
    private RandomClient randomClient;


    @InjectMocks
    private RandomStringBehavior randomStringBehavior;

    @Test
    public void shouldReturnCorrectOperationType(){
        Assertions.assertEquals(randomStringBehavior.getOperationType(), OperationType.RANDOM_STRING);
    };

    @Test
    public void shouldRaiseIfInvalidIntegerOperators() {
        final Map<String, String> operators = new HashMap<>();
        IllegalArgumentException ex;

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(null));
        Assertions.assertEquals("Numeric operators must have numbers key.", ex.getMessage());

        operators.put("num", "a");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [num, len] must be integers.", ex.getMessage());

        operators.put("num", "10");
        operators.put("len", "c");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [num, len] must be integers.", ex.getMessage());
    }

    @Test
    public void shouldRaiseIfInvalidOnOffOperators() {
        final Map<String, String> operators = new HashMap<>();
        IllegalArgumentException ex;

        operators.put("digits", "u");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [digits, upperalpha, loweralpha, unique] must have values [on, off].",
                ex.getMessage());

        operators.clear();
        operators.put("upperalpha", "u");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [digits, upperalpha, loweralpha, unique] must have values [on, off].",
                ex.getMessage());

        operators.clear();
        operators.put("loweralpha", "u");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [digits, upperalpha, loweralpha, unique] must have values [on, off].",
                ex.getMessage());

        operators.clear();
        operators.put("unique", "u");

        ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> randomStringBehavior.applyBehavior(operators));
        Assertions.assertEquals("Operators [digits, upperalpha, loweralpha, unique] must have values [on, off].",
                ex.getMessage());
    }

    @Test
    public void shouldCallClientWithDefaultValues() {
        final Map<String, String> operators = new HashMap<>();

        when(randomClient.getStrings(anyInt(), anyInt(), any(), any(), any(), any()))
                .thenReturn("string1\nstring2\nstring3");;

        randomStringBehavior.applyBehavior(operators);

        verify(randomClient, times(1))
                .getStrings(10,
                        10,
                        "on",
                        "off",
                        "off",
                        "on");
    }

    @Test
    public void shouldCallClientWithCustomValues() {
        final Map<String, String> operators = new HashMap<>();

        when(randomClient.getStrings(anyInt(), anyInt(), any(), any(), any(), any()))
                .thenReturn("string1\nstring2\nstring3");;

        operators.put("num", "8");
        operators.put("len", "33");
        operators.put("digits", "off");
        operators.put("upperalpha", "on");
        operators.put("loweralpha", "on");
        operators.put("unique", "off");

        randomStringBehavior.applyBehavior(operators);

        verify(randomClient, times(1))
                .getStrings(8,
                        33,
                        "off",
                        "on",
                        "on",
                        "off");
    }

    @Test
    public void shouldCorrectlyApplyOperation() {
        final Map<String, String> operators = new HashMap<>();

        when(randomClient.getStrings(anyInt(), anyInt(), any(), any(), any(), any()))
                .thenReturn("string1\nstring2\nstring3");

        Assertions.assertEquals("string1,string2,string3",
                randomStringBehavior.applyBehavior(operators).get("result"));
    }
}
