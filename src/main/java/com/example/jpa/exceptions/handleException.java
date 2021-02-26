package com.example.jpa.exceptions;

import com.example.jpa.support.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice(value = {"com.example.jpa.controllers"})
public class handleException{
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handle(AbstractException e) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(e.getMessage());
        baseResponse.setStatus(e.getStatus().value());
        return baseResponse;
    }
}
