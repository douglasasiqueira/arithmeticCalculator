package com.portfolio.arithmetic.calculator.core.customException;

public class ResourceNotFoundException extends RuntimeException{
    final static String defaultMessage = "Resource not found exception.";

    public ResourceNotFoundException() {
        super(defaultMessage);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
