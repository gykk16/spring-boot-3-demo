package dev.glory.demo.common.code;


import org.springframework.http.HttpStatus;

/**
 * API 응답 코드 인터페이스
 */
public interface BaseResponseCode {

    boolean isSuccess();

    String getCode();

    String getMessage();

    HttpStatus getStatus();

}
