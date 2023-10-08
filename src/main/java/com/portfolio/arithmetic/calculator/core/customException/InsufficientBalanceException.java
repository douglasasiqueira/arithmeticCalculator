package com.portfolio.arithmetic.calculator.core.customException;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
