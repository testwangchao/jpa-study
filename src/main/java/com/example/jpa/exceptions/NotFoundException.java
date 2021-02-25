package com.example.jpa.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
