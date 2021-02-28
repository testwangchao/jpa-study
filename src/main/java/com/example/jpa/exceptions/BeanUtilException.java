package com.example.jpa.exceptions;

import org.springframework.http.HttpStatus;

public class BeanUtilException extends AbstractException{
    public BeanUtilException(String message) {
        super(message);
    }

    public BeanUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
