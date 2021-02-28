package com.example.jpa.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtils {


    /**
     * 将错误校验信息转换为标准的map filed: msg
     * @return HashMap<Filed, msg>
     */
    public static Map<String, String> mapWithFieldError(@NonNull List<FieldError> fieldErrors) {
        if (CollectionUtils.isEmpty(fieldErrors)) {
            return Collections.emptyMap();
        }
        Map<String,String> errMap = new HashMap<>();
        fieldErrors.forEach((error) -> {
            errMap.put(error.getField(), error.getDefaultMessage());
        });
        return errMap;
    }
}
