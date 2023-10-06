package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ForbiddenAccessException.class)
    public ResponseEntity<Object> handleException(ForbiddenAccessException e){
        return new ResponseEntity<>("Forbidden Access Exception : "+e.getMessage(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleException(IllegalArgumentException e){
        return new ResponseEntity<>("Illegal Argument : "+e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> exceptionHandler(NotFoundException e){
        return new ResponseEntity<>("Not Fount : "+e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
