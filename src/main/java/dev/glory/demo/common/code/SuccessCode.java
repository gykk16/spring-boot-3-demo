package dev.glory.demo.common.code;

import lombok.Getter;

import org.springframework.http.HttpStatus;

/**
 * API 성공 응답 코드
 */
@Getter
public enum SuccessCode implements BaseResponseCode {

    SUCCESS(HttpStatus.OK, true, "S00000", "성공"),
    CREATED(HttpStatus.CREATED, true, "S00001", "생성 완료"),
    ACCEPTED(HttpStatus.ACCEPTED, true, "S00002", "접수 완료"),
    ;

    private final HttpStatus status;
    private final boolean    success;
    private final String     code;
    private final String     message;

    SuccessCode(HttpStatus status, boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
