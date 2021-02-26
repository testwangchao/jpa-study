package com.example.jpa.exceptions;

import com.example.jpa.support.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice(basePackages = "com.example.jpa.controllers")
public class CommonException implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        MappingJacksonValue container = getOrCreateContainer(o);
        beforeBodyWriteInternal(container, mediaType, methodParameter, serverHttpRequest, serverHttpResponse);
        return container;
    }

    private MappingJacksonValue getOrCreateContainer(Object body) {
        return body instanceof MappingJacksonValue ? (MappingJacksonValue) body :
                new MappingJacksonValue(body);
    }

    private void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                         MediaType contentType,
                                         MethodParameter returnType,
                                         ServerHttpRequest request,
                                         ServerHttpResponse response) {

        // Get return body
        Object returnBody = bodyContainer.getValue();
        if (returnBody instanceof BaseResponse) {
            BaseResponse<?> baseResponse = (BaseResponse<?>) returnBody;
            response.setStatusCode(HttpStatus.resolve(baseResponse.getStatus()));
            return;
        }
        // Wrap the return body
        BaseResponse<?> baseResponse = BaseResponse.ok(returnBody);
        bodyContainer.setValue(baseResponse);
        response.setStatusCode(HttpStatus.valueOf(baseResponse.getStatus()));
    }
}
