package com.example.jpa.dto.base;

import org.springframework.lang.Nullable;
import java.lang.reflect.ParameterizedType;
import com.example.jpa.utils.ReflectionUtils;
import static com.example.jpa.utils.BeanUtils.transformFrom;

public interface InputConverter<T> {

    default T convertTo(){
        ParameterizedType currentType = parameterizedType();
        Class<T> domain = (Class<T>) currentType.getActualTypeArguments()[0];
        return transformFrom(this, domain);
    }

    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
