package dev.glory.demo.system.annotation.logrequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LogRequest
 * <p>
 * . annotation to save request and response information into DB
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRequest {

    /**
     * action
     */
    RequestAction action();

    /**
     * 로그 출력 여부
     */
    boolean print() default true;

    /**
     * DB 저장 여부
     */
    boolean saveDb() default false;

    /**
     * DB 저장시 response body 저장 여부
     */
    boolean excludeResponseBody() default false;
}
