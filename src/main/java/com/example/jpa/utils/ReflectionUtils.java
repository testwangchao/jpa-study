package com.example.jpa.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {
    public ReflectionUtils() {}

    @Nullable
    public static ParameterizedType getParameterizedTypeBySuperClass(@NonNull Class<?> superClassType, Class<?> extensionClass) {
        if (extensionClass == null) {
            return null;
        }
        return getParameterizedType(superClassType, extensionClass.getGenericSuperclass());
    }

    public static ParameterizedType getParameterizedType(@NonNull Class<?> superType, Type... genericTypes){
        Assert.notNull(superType, "Interface or super type must not be null");
        ParameterizedType currentType = null;
        for (Type genericType:genericTypes) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            if (parameterizedType.getRawType().getTypeName().equals(superType.getTypeName())) {
                currentType = parameterizedType;
                break;
            }
        }
        return currentType;
    }

    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceType,
                                                         Class<?> implementationClass){
        Assert.notNull(interfaceType, "Interface type must not be null");
        Assert.isTrue(interfaceType.isInterface(), "The give type must be an interface");
        if (implementationClass == null) {
            return null;
        }
        ParameterizedType currentType = getParameterizedType(interfaceType, implementationClass.getGenericInterfaces());
        if (currentType != null) {
            // return the current type
            return currentType;
        }

        Class<?> superclass = implementationClass.getSuperclass();

        return getParameterizedType(interfaceType, superclass);
    }
}
