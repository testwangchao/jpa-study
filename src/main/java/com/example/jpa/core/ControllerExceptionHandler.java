package com.example.jpa.core;

import com.example.jpa.exceptions.AbstractException;
import com.example.jpa.exceptions.NotFoundException;
import com.example.jpa.support.BaseResponse;
import com.example.jpa.utils.ExceptionUtils;
import static com.example.jpa.utils.ValidationUtils.mapWithFieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice(value = {"com.example.jpa.controllers"})
public class ControllerExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handle(AbstractException e) {
        BaseResponse<?> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(e.getMessage());
        baseResponse.setStatus(e.getStatus().value());
        return baseResponse;
    }

    /**
     * 校验请求参数
     * @param e 参数是否为空等校验异常
     * @return response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handleConstraintViolationException(MethodArgumentNotValidException e) {
        BaseResponse<Map<String, String>> baseResponse = handleBaseException(e);
        baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponse.setMessage("字段验证错误，请完善后重试！");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        Map<String, String> errMap = mapWithFieldError(errors);
        baseResponse.setData(errMap);
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");

        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(t.getMessage());

        log.error("Captured an exception:", t);

        if (log.isDebugEnabled()) {
            baseResponse.setMessage(ExceptionUtils.getStackTrace(t));
        }

        return baseResponse;
    }
}
