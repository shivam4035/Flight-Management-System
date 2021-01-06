package com.fms.core.exception;

public class FlightNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FlightNotfoundException(String message) {
        super(message);
    }
}
