package dev.glory.demo.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class SystemUtil {

    // @PostConstruct 로 주입
    private static String serverIp;

    /**
     * @return server ip
     */
    public static String getServerIP() {
        return serverIp;
    }

    /**
     * @return client ip
     */
    public static String getClientIP() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return getClientIP(request);
    }

    /**
     * @param request HttpServletRequest
     * @return client ip
     */
    public static String getClientIP(HttpServletRequest request) {
        String[] ipHeaderCandidates = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

        for (String header : ipHeaderCandidates) {
            String ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !StringUtils.startsWithIgnoreCase(ip, "unknown")) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * @return client os
     */
    public static String getClientOS() {

        final String userAgent = getUserAgent();
        final String os;

        if (StringUtils.countOccurrencesOf(userAgent, "windows nt 10.0") > 0) {
            os = "Windows10";
        } else if (StringUtils.countOccurrencesOf(userAgent, "mac") > 0) {
            os = "MAC";
        } else if (StringUtils.countOccurrencesOf(userAgent, "linux") > 0) {
            os = "Linux";
        } else if (StringUtils.countOccurrencesOf(userAgent, "iphone") > 0) {
            os = "iPhone";
        } else if (StringUtils.countOccurrencesOf(userAgent, "ipad") > 0) {
            os = "iPad";
        } else if (StringUtils.countOccurrencesOf(userAgent, "android") > 0) {
            os = "Android";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 6.2") > 0
                || StringUtils.countOccurrencesOf(userAgent, "windows nt 6.3") > 0) {
            os = "Windows8";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 6.1") > 0) {
            os = "Windows7";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 6.0") > 0) {
            os = "WindowsVista";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 5.1") > 0) {
            os = "WindowsXP";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 5.0") > 0) {
            os = "Windows2000";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows nt 4.0") > 0) {
            os = "WindowsNT";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows 98") > 0) {
            os = "Windows98";
        } else if (StringUtils.countOccurrencesOf(userAgent, "windows 95") > 0) {
            os = "Windows95";
        } else {
            os = userAgent;
        }

        return os;
    }

    /**
     * 클라이언트 브라우저 조회
     *
     * @return
     */
    public static String getClientBrowser() {

        final String userAgent = getUserAgent();
        String browser = "Unknown browser";

        if (StringUtils.countOccurrencesOf(userAgent, "edge/") > 0) {
            browser = "Edge";
        } else if (StringUtils.countOccurrencesOf(userAgent, "chrome/") > 0
                && StringUtils.countOccurrencesOf(userAgent, "safari/") > 0) {
            browser = "Google Chrome";
        } else if (StringUtils.countOccurrencesOf(userAgent, "safari/") > 0) {
            browser = "Safari";
        } else if (StringUtils.countOccurrencesOf(userAgent, "firefox/") > 0) {
            browser = "Firefox";
        } else if (StringUtils.countOccurrencesOf(userAgent, "chrome/") > 0
                && StringUtils.countOccurrencesOf(userAgent, "safari/") > 0
                && StringUtils.countOccurrencesOf(userAgent, "opr/") > 0) {
            browser = "Opera";
        } else if (StringUtils.countOccurrencesOf(userAgent, "trident/7.0") > 0) {
            browser = "IE11";
        } else if (StringUtils.countOccurrencesOf(userAgent, "msie 10") > 0) {
            browser = "IE10";
        } else if (StringUtils.countOccurrencesOf(userAgent, "msie 9") > 0) {
            browser = "IE9";
        } else if (StringUtils.countOccurrencesOf(userAgent, "msie 8") > 0) {
            browser = "IE8";
        } else if (StringUtils.countOccurrencesOf(userAgent, "msie 7") > 0) {
            if (StringUtils.countOccurrencesOf(userAgent, "trident/4.0") > 0) {
                browser = "IE8";
            } else if (StringUtils.countOccurrencesOf(userAgent, "trident/5.0") > 0) {
                browser = "IE9";
            } else if (StringUtils.countOccurrencesOf(userAgent, "trident/6.0") > 0) {
                browser = "IE10";
            } else if (StringUtils.countOccurrencesOf(userAgent, "trident/7.0") > 0) {
                browser = "IE11";
            }
        } else if (StringUtils.countOccurrencesOf(userAgent, "thunderbird") > 0) {
            browser = "Thunderbird";
        } else {
            browser = userAgent;
        }

        return browser;
    }

    /**
     * @return user agent
     */
    public static String getUserAgent() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return StringUtils.hasText(request.getHeader("user-agent"))
                ? request.getHeader("user-agent").toLowerCase() : "";
    }

    @PostConstruct
    private static void setServerIp() {
        log.info("# ==> SystemUtil.setServerIp");
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
            log.info("# ==> serverIp = {}", serverIp);
        } catch (UnknownHostException e) {
            serverIp = "UnknownHostException";
        }
    }
}
