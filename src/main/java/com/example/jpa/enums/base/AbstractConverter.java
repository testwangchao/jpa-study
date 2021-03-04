package com.example.jpa.enums.base;

import com.example.jpa.enums.ValueEnum;
import com.example.jpa.utils.ReflectionUtils;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class AbstractConverter<E extends ValueEnum<V>, V>
        implements AttributeConverter<E,V> {

    private final Class<E> clazz;

    @SuppressWarnings("unchecked")
    protected AbstractConverter() {
        Type enumType = Objects.requireNonNull(
                ReflectionUtils.getParameterizedType(AbstractConverter.class, this.getClass()))
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
     * @param code userStatusEnum code
     * @return userStatusEnum desc
     */
    @Override
    public E convertToEntityAttribute(V dbData) {
        return dbData == null ? null:ValueEnum.valueToEnum(clazz, dbData);
    }
}
