package dev.glory.demo.common.code;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements BaseResponseCode {

    AUTH_ERROR(HttpStatus.BAD_REQUEST, false, "ERA999", "인증 오류가 발생했습니다."),
    BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, false, "ERA001", "잘못된 인증 정보입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, false, "ERA002", "사용자를 찾을 수 없습니다."),

    EXISTING_USER(HttpStatus.BAD_REQUEST, false, "ERA101", "사용 불가능한 username 입니다."),
    ;

    private final HttpStatus status;
    private final boolean    success;
    private final String     code;
    private final String     message;

    AuthErrorCode(HttpStatus status, boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
