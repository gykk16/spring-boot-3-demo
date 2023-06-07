package dev.glory.demo.web.external.code;

import lombok.Getter;

import dev.glory.demo.common.code.BaseResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public enum ExternalErrorCode implements BaseResponseCode {

    EXTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "EXT999", "오류가 발생했습니다."),
    TIMEOUT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "EXT901", "오류가 발생했습니다."),
    HTTP_STATUS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "EXT902", "오류가 발생했습니다."),

    RESPONSE_CODE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "EXT101", "응답 코드 오류."),
    RESPONSE_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "EXT102", "응답 데이터 오류."),
    ;

    private final HttpStatus status;
    private final boolean    success;
    private final String     code;
    private final String     message;

    ExternalErrorCode(HttpStatus status, boolean success, String code, String message) {
        this.status = status;
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
