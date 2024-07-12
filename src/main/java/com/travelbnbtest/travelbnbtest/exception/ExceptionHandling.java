package com.travelbnbtest.travelbnbtest.exception;

import com.travelbnbtest.travelbnbtest.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails > resourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest webRequest
    ){
        ErrorDetails error=new ErrorDetails(
                ex.getMessage(),
                new Date(),
                webRequest.getDescription(false)
        );
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails > globalException(
            Exception ex,
            WebRequest webRequest
    ){
        ErrorDetails error=new ErrorDetails(
                ex.getMessage(),
                new Date(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
