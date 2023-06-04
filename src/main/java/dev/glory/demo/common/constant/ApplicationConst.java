package dev.glory.demo.common.constant;


import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.slf4j.MDC;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConst {

    // ================================================================================================================
    // MDC 상수
    // ================================================================================================================
    public static final String REQUEST_TRACE_KEY = "REQ_TRACE_KEY";
    public static final String ACCESS_ID         = "ACCESS_ID_";
    public static final String REQ_LOG_ID        = "REQ_LOG_ID_";
    public static final String REQ_PRINT_ID      = "REQ_PRINT_ID_";

    // ================================================================================================================
    //
    // ================================================================================================================

    public static Optional<String> getMdcTraceKey() {
        return Optional.ofNullable(MDC.get(REQUEST_TRACE_KEY));
    }

    public static Optional<String> getMdcAccessId() {
        return Optional.ofNullable(MDC.get(ACCESS_ID));
    }

    public static Optional<String> getMdcReqLogId() {
        return Optional.ofNullable(MDC.get(REQ_LOG_ID));
    }

    public static Optional<String> getMdcReqPrintId() {
        return Optional.ofNullable(MDC.get(REQ_PRINT_ID));
    }

    // ================================================================================================================
    //
    // ================================================================================================================

}
