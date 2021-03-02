package com.example.jpa.utils;

import com.example.jpa.exceptions.BeanUtilException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils {

    /**
     * @param source 被赋值属性的对象
     * @param target 目标对象
     */
    public static void updateProperties(@NonNull Object source, @NonNull Object target) {
        Assert.notNull(source, "source object must not be null");
        Assert.notNull(target, "target object must not be null");
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (BeansException e) {
            throw new BeanUtilException("Failed to copy properties", e);
        }
    }

    /**
     * 获取source对象中不等于null的属性的值的数组
     *
     * @param source 被复制的对象
     * @return null name array of property
     */
    public static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    /**
     * 获取source对象中不等于null的属性的值的集合
     *
     * @param source 被复制的对象
     * @return null name set of property
     */
    public static Set<String> getNullPropertyNameSet(@NonNull Object source) {
        Assert.notNull(source, "source object must not be null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }
        return emptyNames;
    }

    public static <T> T transformFrom(@NonNull Object source,  @NonNull Class<T> targetClass) {
        Assert.notNull(targetClass, "Target class must not be null");

        if (source == null) {
            return null;
        }

        try {
            // New instance for the target class
            T targetInstance = targetClass.getConstructor().newInstance();
            // Copy properties
            org.springframework.beans.BeanUtils
                    .copyProperties(source, targetInstance, getNullPropertyNames(source));
            // Return the target instance
            return targetInstance;
        } catch (Exception e) {
            throw new BeanUtilException(
                    "Failed to new " + targetClass.getName() + " instance or copy properties", e);
        }
    }
}
