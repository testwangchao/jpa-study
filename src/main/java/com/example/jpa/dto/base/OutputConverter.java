package com.example.jpa.dto.base;

import org.springframework.lang.NonNull;
import static com.example.jpa.utils.BeanUtils.updateProperties;

public interface OutputConverter<DtoT extends OutputConverter<DtoT,D>, D> {
    default  <T extends DtoT> T convertFrom(@NonNull D domain) {
        updateProperties(domain, this);
        return (T) this;
    };
}
