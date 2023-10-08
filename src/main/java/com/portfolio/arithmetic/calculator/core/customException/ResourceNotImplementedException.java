package com.portfolio.arithmetic.calculator.core.customException;

public class ResourceNotImplementedException extends RuntimeException{
    final static String defaultMessage = "Resource not implemented exception.";

    public ResourceNotImplementedException() {
        super(defaultMessage);
    }

    public ResourceNotImplementedException(String message) {
        super(message);
    }

}
