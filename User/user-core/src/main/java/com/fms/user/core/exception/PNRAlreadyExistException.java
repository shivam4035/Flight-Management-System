package com.fms.user.core.exception;

public class PNRAlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PNRAlreadyExistException(String message) {
        super(message);
    }
}
