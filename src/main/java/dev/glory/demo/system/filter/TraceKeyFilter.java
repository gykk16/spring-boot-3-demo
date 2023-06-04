package dev.glory.demo.system.filter;


import static dev.glory.demo.common.constant.ApplicationConst.REQUEST_TRACE_KEY;
import static dev.glory.demo.common.constant.ApplicationConst.getMdcTraceKey;
import static dev.glory.demo.common.util.LogStringUtil.LOG_LINE;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.util.SystemUtil;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "TraceKeyFilter", urlPatterns = "/**")
@Slf4j
public class TraceKeyFilter extends OncePerRequestFilter {

    private static final String REQUEST_TRACE_KEY_HEADER = "Request-Trace-Key";
    private static final String REQUEST_START_TIME       = "REQ_S_TIME_MS";
    private static final long   WARN_PROCESS_TIME_MS     = 2_000;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            MDC.put(REQUEST_START_TIME, String.valueOf(System.currentTimeMillis()));
            MDC.put(REQUEST_TRACE_KEY, String.valueOf(UUID.randomUUID()));

            log.info(
                    "# REQ START #######################################################################################################");

            logDefaultParameters(request);

            response.setHeader(REQUEST_TRACE_KEY_HEADER,
                    getMdcTraceKey().orElse("00000000-0000-0000-0000-nokeydefined"));

            // ==============================
            // doFilter
            // ==============================
            filterChain.doFilter(request, response);

            long requestEndTime = System.currentTimeMillis();
            long requestProcessTime = requestEndTime - Long.parseLong(MDC.get(REQUEST_START_TIME));
            log.info("# Request Process Time = {}ms", requestProcessTime);

            if (requestProcessTime >= WARN_PROCESS_TIME_MS) {
                log.warn("# 요청 처리 시간 {}ms 이상", WARN_PROCESS_TIME_MS);
            }

        } finally {
            log.info(
                    "# REQ END   #######################################################################################################");
            MDC.clear();
        }
    }

    /**
     * 요청 기본 정보 로그 출력
     *
     * @param request request
     */
    private void logDefaultParameters(HttpServletRequest request) {

        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();
        String referer = request.getHeader("Referer");
        String accept = request.getHeader("Accept");
        String userAgent = request.getHeader("user-agent");
        String authorization = request.getHeader("Authorization");

        log.info("# RequestURI = {} , {}", method, requestURI);
        log.info("# Referer = {}", referer);
        log.info("# Content-Type = {} , Accept = {}", contentType, accept);
        log.info("# User-Agent = {}", userAgent);
        if (StringUtils.hasText(authorization)) {
            log.info("# Authorization = {}", authorization);
        }
        log.info("# ServerIp = {} , ClientIp = {}", SystemUtil.getServerIP(), SystemUtil.getClientIP(request));
        log.info(LOG_LINE);
    }
}
