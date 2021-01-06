package com.fms.core.exception;

public class FlightAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FlightAlreadyExistException(String message) {
        super(message);
    }
}
