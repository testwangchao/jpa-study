package com.example.jpa.core;

import com.example.jpa.support.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一接口返回格式
 */
@Slf4j
@ControllerAdvice(basePackages = "com.example.jpa.controllers")
public class CommonResultControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) {
            return BaseResponse.ok();
        }
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
        BaseResponse<?> baseResponse;
        log.info(returnBody.getClass().getTypeName());
        log.info(Arrays.toString(returnBody.getClass().getInterfaces()));
        if (returnBody instanceof BaseResponse) {
            baseResponse = (BaseResponse<?>) returnBody;
            response.setStatusCode(HttpStatus.resolve(baseResponse.getStatus()));
            return;
        } else if (returnBody instanceof Page){
            Map<String, Object> data = handlePage((Page<?>) returnBody);
            baseResponse = BaseResponse.ok(data);
        } else {
            baseResponse = BaseResponse.ok(returnBody);
        }
        // Wrap the return body
        bodyContainer.setValue(baseResponse);
        response.setStatusCode(HttpStatus.valueOf(baseResponse.getStatus()));
    }

    /**
     * handle Page Data
     * @param page Page
     */
    public Map<String, Object> handlePage(Page<?> page) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", page.getContent());
        map.put("total", page.getTotalElements());
        map.put("pages", page.getTotalPages());
        return map;
    }
}
