package com.portfolio.arithmetic.calculator.core.customException;

public class InvalidOperatorsException extends RuntimeException{
    final static String defaultMessage = "Operators are invalid for this operation.";

    public InvalidOperatorsException() {
        super(defaultMessage);
    }

    public InvalidOperatorsException(String message) {
        super(message);
    }
}
