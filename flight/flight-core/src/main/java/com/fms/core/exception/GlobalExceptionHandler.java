package com.fms.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = FlightNotfoundException.class)
    public ResponseEntity<Object> exception(FlightNotfoundException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FlightAlreadyExistException.class)
    public ResponseEntity<Object> exception(FlightAlreadyExistException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FlightFareErrorExcpetion.class)
    public ResponseEntity<Object> exception(FlightFareErrorExcpetion exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FlightSeatsException.class)
    public ResponseEntity<Object> exception(FlightSeatsException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
    }

}
