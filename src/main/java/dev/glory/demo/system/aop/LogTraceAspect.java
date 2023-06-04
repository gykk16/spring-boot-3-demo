package dev.glory.demo.system.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.system.aop.logtrace.LogTrace;
import dev.glory.demo.system.aop.logtrace.TraceStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class LogTraceAspect {

    private final LogTrace logTrace;


    @Around("dev.glory.demo.system.aop.pointcut.Pointcuts.logTracePointcut()")
    public Object doTimer(ProceedingJoinPoint joinPoint) throws Throwable {

        TraceStatus status = null;
        try {
            status = logTrace.begin(joinPoint.getSignature().toShortString());
            Object result = joinPoint.proceed();
            logTrace.end(status);

            return result;

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
