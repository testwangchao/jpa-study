package com.example.jpa.aops;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedManagerPower {
    int mode() default 1;
}
