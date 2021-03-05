package com.example.jpa.aops;

import com.example.jpa.exceptions.NotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CustomAop {

    @Pointcut("execution(* com.example.jpa.controllers..*.*(..))"
            + "&& @annotation(com.example.jpa.aops.NeedManagerPower)")
    public void custom(){}

    //在本类的login执行之前
    @Before("custom()")
    public void beforelogin(JoinPoint joinPoint) {
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println("before");
    }
    //在本类的login执行之后执行
    @After("custom()")
    public void afterlogin(){System.out.println("after");}

    @Around("custom() && @annotation(needManagerPower)))")
    public Object checklogin(ProceedingJoinPoint proceedingJoinPoint, NeedManagerPower needManagerPower) throws Throwable {
        if (needManagerPower.mode()!=1){
            throw new NotFoundException("禁止访问");
        }
        return "3423";
    }
}
