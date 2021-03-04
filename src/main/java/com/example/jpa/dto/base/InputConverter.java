package com.example.jpa.dto.base;

import org.springframework.lang.Nullable;
import java.lang.reflect.ParameterizedType;
import com.example.jpa.utils.ReflectionUtils;
import static com.example.jpa.utils.BeanUtils.transformFrom;
import static com.example.jpa.utils.BeanUtils.updateProperties;

public interface InputConverter<D> {

    /**
     * 请求参数内的字段的值复制到数据表的对应实体类，
     * @return 被赋值之后的数据表的实体类对象
     */
    default D convertTo(){
        ParameterizedType currentType = parameterizedType();
        Class<D> domain = (Class<D>) currentType.getActualTypeArguments()[0];
        return transformFrom(this, domain);
    }

    /**
     * 更新已经存在的数据表实体类
     * 比如更新用户信息，将用户的用户名更新到对应的已存在的用户对象
     * @param domain 数据表实体类
     */
    default void update(D domain){
        updateProperties(this, domain);
    }

    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());
    }
}
