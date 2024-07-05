package com.dinitProject.healthcheckMonitoringApp.exceptions;

import com.dinitProject.healthcheckMonitoringApp.exceptions.conflict.ConflictException;
import com.dinitProject.healthcheckMonitoringApp.exceptions.notfound.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex,ex.getHttpStatus());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }

    /* @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleException(RuntimeException ex) {
        return switch (ex) {
            case NotFoundException exception -> new ResponseEntity<>(exception,HttpStatus.NOT_FOUND);
            case ConflictException exception -> new ResponseEntity<>(exception,HttpStatus.CONFLICT);
            default              -> new ResponseEntity<>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
        };
    } */

}
