package dev.glory.demo.common.code;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseResponseCode {

    //
    // 인증 오류 [4xx]
    AUTH_ERROR(HttpStatus.FORBIDDEN, false, "ERR101", "인증 오류가 발생 했습니다."),
    BAD_CREDENTIAL(HttpStatus.FORBIDDEN, false, "ERR102", "인증 정보가 잘못 되었습니다."),
    //
    // 요청 오류 [2xx]
    METHOD_NOT_SUPPORTED(HttpStatus.NOT_FOUND, false, "ERR201", "요청 받은 리소스를 찾을 수 없습니다."),
    MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, false, "ERR202", "요청 받은 리소스를 읽을 수 없습니다."),
    //
    // 권한 오류 [3xx]
    FORBIDDEN(HttpStatus.FORBIDDEN, false, "ERR301", "접근 권한이 없습니다."),
    //
    // 시스템 오류
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "ERR999", "오류가 발생 했습니다."),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "SYS999", "시스템 오류가 발생 했습니다."),
    ;

    private final HttpStatus status;
    private final boolean    success;
    private final String     code;
    private final String     message;

    ErrorCode(HttpStatus status, boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
