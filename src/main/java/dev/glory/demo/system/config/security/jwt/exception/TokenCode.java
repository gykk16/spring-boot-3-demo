package dev.glory.demo.system.config.security.jwt.exception;

import lombok.Getter;

import dev.glory.demo.common.code.BaseResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenCode implements BaseResponseCode {

    TOKEN_ERROR(HttpStatus.UNAUTHORIZED, false, "TOK999", "인증 오류 입니다."),
    TOKEN_VALIDATION_ERROR(HttpStatus.UNAUTHORIZED, false, "TOK900", "인증 정보 검증 오류 입니다."),


    NO_TOKEN(HttpStatus.UNAUTHORIZED, false, "TOK001", "인증 정보가 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, false, "TOK002", "만료된 인증 정보 입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, false, "TOK003", "지원 하지 않은 인증 정보 입니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, false, "TOK004", "잘못된 타입의 인증 정보 입니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, false, "TOK005", "잘못된 인증 정보 입니다."),
    AUTH_ERROR(HttpStatus.UNAUTHORIZED, false, "TOK006", "인증 오류 입니다."),


    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, false, "TOK201", "refresh token 이 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, false, "TOK202", "만료된 인증 정보 입니다."),
    ;


    private final HttpStatus status;
    private final boolean    success;
    private final String     code;
    private final String     message;

    TokenCode(HttpStatus status, boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
