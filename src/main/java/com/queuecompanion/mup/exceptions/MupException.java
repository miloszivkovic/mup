package com.queuecompanion.mup.exceptions;

import org.springframework.http.HttpStatus;

public class MupException extends RuntimeException {
    private final HttpStatus status;

    public MupException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MupException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public MupException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
