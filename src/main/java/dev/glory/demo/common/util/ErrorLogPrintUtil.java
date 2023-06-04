package dev.glory.demo.common.util;

import static lombok.AccessLevel.PRIVATE;

import jakarta.servlet.http.HttpServletRequest;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.code.BaseResponseCode;
import org.apache.commons.lang3.exception.ExceptionUtils;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class ErrorLogPrintUtil {

    /**
     * error 로그 출력
     *
     * @param request request
     * @param code    BaseResponseCode
     * @param e       Exception
     */
    public static void logError(HttpServletRequest request, BaseResponseCode code, Exception e) {
        logError(request, code, e, true);
    }

    /**
     * error 로그 출력
     *
     * @param request    request
     * @param code       BaseResponseCode
     * @param e          Exception
     * @param printTrace stack trace 출력 여부
     */
    public static void logError(HttpServletRequest request, BaseResponseCode code, Exception e, boolean printTrace) {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        log.error(LogStringUtil.logTitle("ERROR LOG"));

        log.error("# ==> RequestURI = {} , {}", method, requestURI);
        log.error("# ==> ServerIp = {} , ClientIp = {}", SystemUtil.getServerIP(), SystemUtil.getClientIP());

        log.error("# ==> responseCode = {} , {}", code.getCode(), code.getMessage());
        log.error("# ==> httpStatus = {}", code.getStatus());
        log.error("# ==> exception = {} , {}", e.getClass().getSimpleName(), e.getMessage());
        log.error("# ==> rootCause = {} , {}", rootCause.getClass().getSimpleName(), rootCause.getMessage());
        if (printTrace) {
            log.error("# ==> cause - ", e);
        }

        log.error(LogStringUtil.LOG_LINE);
    }
}
