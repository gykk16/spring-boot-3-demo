package dev.glory.demo.common.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * 서블릿 유틸
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpServletUtil {


    /**
     * 세션 Attribute 생성
     *
     * @param key   세션 Attribute key 값
     * @param value 저장 할 객체
     */
    public static void createSession(String key, Object value) {
        HttpSession session = getRequest().getSession();
        session.setAttribute(key, value);
    }

    /**
     * 세션 삭제
     */
    public static void removeSession() {
        HttpSession session = getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * 세션 Attribute 조회
     *
     * @param key Attribute Key 값
     * @return 세션 조회 객체
     */
    public static Object getSessionAttribute(String key) {
        return getRequest().getSession().getAttribute(key);
    }

    /**
     * 세션 Attribute 삭제
     *
     * @param key Attribute Key 값
     * @return 세션 조회 객체
     */
    public static void removeSessionAttribute(String key) {
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
                .removeAttribute(key, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 세션 존재 여부 확인
     *
     * @return 세션이 살아있으면 true, null 이면 false
     */
    public static boolean isExistingSession(String key) {
        if (getRequest().getSession() == null) {
            return false;
        }
        return getSessionAttribute(key) != null;
    }

    /**
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * @return request parameters
     */
    public static String getRequestParams() {

        StringBuilder requestParameters = new StringBuilder();
        getRequest().getParameterMap().forEach((key, value) -> {
            if (key.equalsIgnoreCase("PASSWORD") || key.equalsIgnoreCase("PWD")) {
                requestParameters.append(key).append("=").append("[****]").append(" ");
            } else {
                requestParameters.append(key).append("=").append(Arrays.toString(value)).append(" ");
            }
        });

        return requestParameters.toString();
    }

    /**
     * @return request body
     */
    public static String getRequestBody() throws IOException {

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        return String.valueOf(objectMapper.readTree(getRequest().getInputStream()));
    }

    /**
     * @return response body
     */
    public static String getResponseBody() {

        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper)getResponse();
        String payload = "";
        if (wrapper != null) {
            wrapper.setCharacterEncoding(StandardCharsets.UTF_8.name());
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, StandardCharsets.UTF_8);
            }
        }
        return payload;
    }
}
