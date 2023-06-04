package dev.glory.demo.common;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.glory.demo.common.code.BaseResponseCode;
import dev.glory.demo.common.code.ErrorCode;
import dev.glory.demo.common.code.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

@Getter
public class RestResponseEntity {

    /**
     * 성공 여부
     */
    private final boolean       success;
    /**
     * 응답 코드
     */
    private final String        code;
    /**
     * 응답 메시지
     */
    private final String        message;
    /**
     * 응답 일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime responseDt;
    /**
     * data
     */
    private final Object        data;

    @Builder
    private RestResponseEntity(BaseResponseCode code, String message, Object responseData) {
        this.success = code.isSuccess();
        this.code = code.getCode();
        this.message = StringUtils.hasText(message) ? message : code.getMessage();
        this.responseDt = LocalDateTime.now();
        this.data = responseData;
    }

    public static ResponseEntity<RestResponseEntity> ofSuccess() {
        return of(SuccessCode.SUCCESS);
    }

    public static ResponseEntity<RestResponseEntity> ofError() {
        return of(ErrorCode.ERROR);
    }

    public static ResponseEntity<RestResponseEntity> ofException(Exception e) {
        return of(ErrorCode.ERROR, e.getClass().getSimpleName(), e.getMessage());
    }

    public static ResponseEntity<RestResponseEntity> of(BaseResponseCode code) {
        return of(code, code.getMessage(), null);
    }

    public static ResponseEntity<RestResponseEntity> of(BaseResponseCode code, String message) {
        return of(code, message, null);
    }

    public static ResponseEntity<RestResponseEntity> of(BaseResponseCode code, Object responseData) {
        return of(code, code.getMessage(), responseData);
    }

    public static ResponseEntity<RestResponseEntity> of(BaseResponseCode code, String message, Object responseData) {
        return ResponseEntity
                .status(code.getStatus())
                .body(RestResponseEntity.builder()
                        .code(code)
                        .message(message)
                        .responseData(responseData)
                        .build());
    }

}
