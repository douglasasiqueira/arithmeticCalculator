package com.portfolio.arithmetic.calculator.core.customException;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
}
