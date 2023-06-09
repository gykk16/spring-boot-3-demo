package dev.glory.demo.system.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import dev.glory.demo.common.RestResponseEntity;
import dev.glory.demo.common.code.AuthErrorCode;
import dev.glory.demo.common.code.BaseResponseCode;
import dev.glory.demo.common.code.ErrorCode;
import dev.glory.demo.common.exception.BizException;
import dev.glory.demo.common.exception.BizRuntimeException;
import dev.glory.demo.common.util.ErrorLogPrintUtil;
import dev.glory.demo.system.config.security.jwt.exception.CustomJwtException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    // ================================================================================================================
    //
    // ================================================================================================================
    @ExceptionHandler(BizRuntimeException.class)
    public ResponseEntity<RestResponseEntity> bizRuntimeException(HttpServletRequest request, BizRuntimeException e) {

        return getDefaultResponseEntity(request, e.getCode(), e, true, e.isSaveDb());
    }

    @ExceptionHandler(BizException.class)
    public ResponseEntity<RestResponseEntity> bizException(HttpServletRequest request, BizRuntimeException e) {

        return getDefaultResponseEntity(request, e.getCode(), e, true, e.isSaveDb());
    }

    // ================================================================================================================
    //
    // ================================================================================================================

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestResponseEntity> authenticationExceptionHandler(HttpServletRequest request,
            AuthenticationException e) {

        BaseResponseCode code = AuthErrorCode.AUTH_ERROR;
        if (e instanceof BadCredentialsException) {
            code = AuthErrorCode.BAD_CREDENTIALS;
        } else if (e instanceof CustomJwtException customJwtException){
            code = customJwtException.getCode();
        }

        return getDefaultResponseEntity(request, code, e, true);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestResponseEntity> accessDeniedExceptionHandler(HttpServletRequest request,
            AccessDeniedException e) {

        BaseResponseCode code = AuthErrorCode.ACCESS_DENIED;

        return getDefaultResponseEntity(request, code, e, true);
    }

    // ================================================================================================================
    //
    // ================================================================================================================
    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponseEntity> bindExceptionHandler(HttpServletRequest request, BindException e) {

        BaseResponseCode code = ErrorCode.VALIDATION_ERROR;

        ErrorLogPrintUtil.logError(request, code, e, false);

        // [{"field": "오류 필드명", "message": "오류 내용", "rejectedValue": "전달된 오류 값"}, {...}, ...]
        List<Map<String, String>> responseList = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> {

            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("field", fieldError.getField());

            if (fieldError.contains(TypeMismatchException.class)) {
                errorMap.put("message", "허용 되지 않은 타입.");
            } else {
                errorMap.put("message", fieldError.getDefaultMessage());
            }
            errorMap.put("rejectedValue", (String)fieldError.getRejectedValue());
            responseList.add(errorMap);
        });

        // !!! errorCode 의 메시지를 반환 - errorCode.getMessage()
        // !!! data 에 responseList 반환
        return RestResponseEntity.of(code, responseList);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestResponseEntity> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request,
            Exception e) {

        return getDefaultResponseEntity(request, ErrorCode.METHOD_NOT_SUPPORTED, e, false);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<RestResponseEntity> httpMessageConversionExceptionHandler(HttpServletRequest request,
            Exception e) {

        return getDefaultResponseEntity(request, ErrorCode.MESSAGE_NOT_READABLE, e, true);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<RestResponseEntity> typeMismatchExceptionHandler(HttpServletRequest request,
            Exception e) {

        return getDefaultResponseEntity(request, ErrorCode.MESSAGE_NOT_READABLE, e, false);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponseEntity> exceptionHandler(HttpServletRequest request, Exception e) {

        return getDefaultResponseEntity(request, ErrorCode.ERROR, e, true, true);
    }

    // ================================================================================================================
    //
    // ================================================================================================================
    private ResponseEntity<RestResponseEntity> getDefaultResponseEntity(HttpServletRequest request,
            BaseResponseCode errorCode, Exception e, boolean printStackTrace) {

        return getDefaultResponseEntity(request, errorCode, e, printStackTrace, false);
    }

    private ResponseEntity<RestResponseEntity> getDefaultResponseEntity(HttpServletRequest request,
            BaseResponseCode errorCode, Exception e, boolean printStackTrace, boolean saveDb) {

        ErrorLogPrintUtil.logError(request, errorCode, e, printStackTrace);
        if (saveDb) {
            saveErrorLog(request, e, errorCode);
        }
        return RestResponseEntity.of(errorCode);
    }

    private void saveErrorLog(HttpServletRequest request, Exception ex, BaseResponseCode errorCode) {
        saveErrorLog(request, ex, errorCode.getStatus());
    }

    /**
     * error db 저장
     *
     * @param request    HttpServletRequest
     * @param ex         exception
     * @param httpStatus http status
     */
    private void saveErrorLog(HttpServletRequest request, Exception ex, HttpStatus httpStatus) {
        // ErrorLogPrinter
        // errorLogService.saveErrorLog(request, ex, httpStatus.value(), appProperties.getAlias());
    }
}
