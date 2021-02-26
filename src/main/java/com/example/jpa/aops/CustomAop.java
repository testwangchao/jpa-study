package com.example.jpa.aops;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CustomAop {

    @Pointcut("execution(* com.example.jpa.controller..*.*(..))")
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

    @Around("@annotation(NeedManagerPower)))")
    public Object checklogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println(proceedingJoinPoint.proceed());
        System.out.println("around");
        return "3423";
    }
}
