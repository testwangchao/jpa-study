package com.example.jpa.enums;


import com.example.jpa.enums.base.AbstractConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserStatusConverter extends AbstractConverter<UserStatusEnum, Integer> {
}
