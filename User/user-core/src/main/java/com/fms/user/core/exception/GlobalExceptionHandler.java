package com.fms.user.core.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = PNRAlreadyExistException.class)
    public ResponseEntity<Object> exception(PNRAlreadyExistException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = BookingNotFoundException.class)
    public ResponseEntity<Object> exception(BookingNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingClientException.class)
    public ResponseEntity<Object> exception(BookingClientException exception, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .details(request.getDescription(false))
                .timestamp(new Date())
                .message(exception.getMessage())

                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


}
