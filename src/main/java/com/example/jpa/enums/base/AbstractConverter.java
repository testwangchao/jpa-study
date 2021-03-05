package com.example.jpa.enums.base;

import com.example.jpa.enums.UserStatusEnum;
import com.example.jpa.enums.ValueEnum;
import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.utils.ReflectionUtils;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractConverter<E extends ValueEnum<V>, V>
        implements AttributeConverter<E,V> {

    private final Class<E> clazz;

    @SuppressWarnings("unchecked")
    protected AbstractConverter() {
        Type enumType = Objects.requireNonNull(
                ReflectionUtils.getParameterizedTypeBySuperClass(AbstractConverter.class, this.getClass()))
                .getActualTypeArguments()[0];
        this.clazz = (Class<E>) enumType;
    }


    /**
     * 插入数据库时的字段是code
     * @param attribute userStatusEnum
     * @return userStatusEnum code
     */
    @Override
    public V convertToDatabaseColumn(E attribute) {
        return attribute == null? null:attribute.getValue();
    }

    /**
     * 转换到实体上是desc
     * @param dbData userStatusEnum code
     * @return userStatusEnum desc
     */
    @Override
    public E convertToEntityAttribute(V dbData) {
        return dbData == null ? null:ValueEnum.valueToEnum(clazz, dbData);
    }

    /**
     * 将请求参数的status状态映射到对应的枚举类上
     * @return
     */
    public Map<Object, E> mapEnum(Integer viewEnum){
        Map<Object, E> map = new HashMap<>();
        for (E enums: clazz.getEnumConstants()){
            map.put(enums.getValue(), enums);
        }
        if (!map.containsKey(viewEnum)){
            throw new NotFoundException(String.format("error status: %s", viewEnum));
        }
        return map;
    }
}
