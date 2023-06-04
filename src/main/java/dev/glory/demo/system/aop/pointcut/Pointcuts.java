package dev.glory.demo.system.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("@annotation(dev.glory.demo.system.annotation.ExcludeLogTrace)")
    public void excludeLogTraceAnnotation() {
    }

    @Pointcut("@annotation(dev.glory.demo.system.annotation.LogTrace)")
    public void logTraceAnnotation() {
    }

    @Pointcut("execution(* dev.glory.demo..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* dev.glory.demo..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("execution(* dev.glory.demo..*Repository.*(..))")
    public void allRepository() {
    }

    @Pointcut("(allController() || allService() || logTraceAnnotation()) && !excludeLogTraceAnnotation()")
    public void logTracePointcut() {
    }

}
