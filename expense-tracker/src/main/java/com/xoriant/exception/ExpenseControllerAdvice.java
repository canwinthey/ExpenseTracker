package com.xoriant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExpenseControllerAdvice {

    @ExceptionHandler(value = {ExpenseValidationException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ExpenseValidationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_ACCEPTABLE);
    }
}
