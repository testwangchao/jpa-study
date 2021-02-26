package com.example.jpa.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({ IndexOutOfBoundsException.class })
    @ResponseBody
    public String handleIndexOutOfBoundsException(Exception e) {
        e.printStackTrace();
        return "testArrayIndexOutOfBoundsException";
    }
}
