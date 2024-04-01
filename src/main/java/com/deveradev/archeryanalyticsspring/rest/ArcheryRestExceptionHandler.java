package com.deveradev.archeryanalyticsspring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ArcheryRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ArcheryErrorResponse> handleResponse(ArcherNotFoundException exc) {
        ArcheryErrorResponse error = new ArcheryErrorResponse();

        error.status = HttpStatus.NOT_FOUND.value();
        error.message = exc.getMessage();
        error.timeStamp = System.currentTimeMillis();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}