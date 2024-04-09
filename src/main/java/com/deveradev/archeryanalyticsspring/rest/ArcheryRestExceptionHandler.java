package com.deveradev.archeryanalyticsspring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ArcheryRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ArcheryErrorResponse> handleResponse(NotFoundException exc) {
        ArcheryErrorResponse error = new ArcheryErrorResponse();

        error.status = HttpStatus.NOT_FOUND.value();
        error.message = exc.getMessage();
        error.timeStamp = System.currentTimeMillis();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ArcheryErrorResponse> handleResponse(BadRequestException exc) {
        ArcheryErrorResponse error = new ArcheryErrorResponse();

        error.status = HttpStatus.BAD_REQUEST.value();
        error.message = exc.getMessage();
        error.timeStamp = System.currentTimeMillis();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ArcheryErrorResponse> handleResponse(MethodArgumentNotValidException exc) {
        ArcheryErrorResponse error = new ArcheryErrorResponse();

        error.status = HttpStatus.BAD_REQUEST.value();
        error.timeStamp = System.currentTimeMillis();

        // Extract and set the specific validation message
        List<FieldError> fieldErrors = exc.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            error.message = fieldErrors.get(0).getDefaultMessage();
        } else {
            error.message = "An unexpected validation error occurred."; // Default message for unexpected errors
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
