package dev.glory.demo.system.interceptor;

import static dev.glory.demo.common.util.HttpServletUtil.getRequestBody;
import static dev.glory.demo.common.util.HttpServletUtil.getRequestParams;
import static dev.glory.demo.common.util.LogStringUtil.logTitle;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 정보 로깅 인터셉터
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info(logTitle("InfoInterceptor [preHandle]"));
        logRequestParameters();
        logRequestBody();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        log.info(logTitle("InfoInterceptor [afterCompletion]"));

        if (ex != null) {
            log.error("# ==> afterCompletion Error!", ex);
            response.sendRedirect("error");
        }
    }

    /**
     * 요청 파라미터 로그 출력
     * <p>
     * . query string, form data
     */
    private void logRequestParameters() {
        String requestParameters = getRequestParams();
        if (StringUtils.hasText(requestParameters)) {
            log.info("# ==> requestParameters = {}", requestParameters);
        }
    }

    /**
     * 요청 request body 로그 출력
     */
    private void logRequestBody() throws IOException {
        String requestBody = getRequestBody();
        if (StringUtils.hasText(requestBody)) {
            log.info("# ==> requestBody = {}", requestBody);
        }
    }
}
