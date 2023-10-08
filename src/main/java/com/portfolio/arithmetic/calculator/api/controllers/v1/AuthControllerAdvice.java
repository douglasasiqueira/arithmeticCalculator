package com.portfolio.arithmetic.calculator.api.controllers.v1;

import com.portfolio.arithmetic.calculator.core.customException.AuthenticationException;
import com.portfolio.arithmetic.calculator.core.customException.InsufficientBalanceException;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotFoundException;
import com.portfolio.arithmetic.calculator.core.customException.ResourceNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getBody());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler({ResourceNotImplementedException.class})
    public ResponseEntity<?> handleResourceNotImplementedException() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @ExceptionHandler({InsufficientBalanceException.class})
    public ResponseEntity<?> handleInsufficientBalanceException() {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }
}
