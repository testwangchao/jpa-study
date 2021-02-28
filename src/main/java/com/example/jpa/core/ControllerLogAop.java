package com.example.jpa.core;

import com.example.jpa.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ControllerLogAop {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restController(){};

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controller(){};

    /**
     * 环绕通知=前置+目标方法执行+后置通知
     * proceed方法就是用于启动目标方法执行的.
     * @param joinPoint joinPoint
     * @return Object
     * @throws Throwable Throwable
     */
    @Around("controller() || restController()")
    public Object controller(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        System.out.println(log.isDebugEnabled());
        if (method == null || !log.isDebugEnabled()) {
            // 目标方法执行完的返回值
            return joinPoint.proceed();
        }
        // 获取
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // 获取请求属性
        ServletRequestAttributes requestArgs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(requestArgs).getRequest();

        // StopWatch获取接口响应时间
        final StopWatch watch = new StopWatch(request.getRequestURI());

        watch.start("PrintRequest");
        printRequestLog(request, className, methodName, args);
        watch.stop();

        watch.start(className + "#" + methodName);
        final Object returnObj = joinPoint.proceed();
        watch.stop();

        watch.start("PrintResponse");
        printResponseLog(className, methodName, returnObj);
        watch.stop();
        if (log.isDebugEnabled()) {
            log.debug("Usage:\n{}", watch.prettyPrint());
        }
        return returnObj;
    }

    private void printRequestLog(HttpServletRequest request, String clazzName, String methodName,
    Object[] args) throws JsonProcessingException {
        log.debug("Request URL: [{}], URI: [{}], Request Method: [{}], IP: [{}]",
                request.getRequestURL(),
                request.getRequestURI(),
                request.getMethod(),
                ServletUtil.getClientIP(request));
        if (args == null || !log.isDebugEnabled()) {
            return;
        }
        boolean shouldNotLog = false;
        for (Object arg : args) {
            if (arg == null
                    || arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse
                    || arg instanceof MultipartFile
                    || arg.getClass().isAssignableFrom(MultipartFile[].class)) {
                shouldNotLog = true;
                break;
            }
        }

        if (!shouldNotLog) {
            String requestBody = JsonUtils.objectToJson(args);
            log.debug("{}.{} Parameters: [{}]", clazzName, methodName, requestBody);
        }
    }

    private void printResponseLog(String className, String methodName,
                                        Object returnObj) throws JsonProcessingException {
        if (log.isDebugEnabled()){
            String returnData = "";
            if (returnObj != null){
                if (returnObj instanceof ResponseEntity) {
                    ResponseEntity<?> responseEntity = (ResponseEntity<?>) returnObj;
                    if (responseEntity.getBody() instanceof Resource) {
                        returnData = "[ BINARY DATA ]";
                    } else if (responseEntity.getBody() != null) {
                        returnData = toString(responseEntity.getBody());
                    }
                } else {
                    returnData = toString(returnObj);
                }
            }
            log.debug("{}.{} Response: [{}]", className, methodName, returnData);
        }
    }

    @NonNull
    private String toString(@NonNull Object obj) throws JsonProcessingException {
        Assert.notNull(obj, "Return object must not be null");

        String toString;
        if (obj.getClass().isAssignableFrom(byte[].class) && obj instanceof Resource) {
            toString = "[ BINARY DATA ]";
        } else {
            toString = JsonUtils.objectToJson(obj);
        }
        return toString;
    }
}
