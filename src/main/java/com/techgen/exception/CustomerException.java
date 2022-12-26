package com.techgen.exception;

import org.springframework.http.HttpStatus;

public class CustomerException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public CustomerException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
