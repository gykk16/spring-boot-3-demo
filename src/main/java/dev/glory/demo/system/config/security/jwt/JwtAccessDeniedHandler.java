package dev.glory.demo.system.config.security.jwt;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.glory.demo.common.RestResponseEntity;
import dev.glory.demo.common.code.AuthErrorCode;
import dev.glory.demo.common.code.BaseResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {

        writeResponse(response, AuthErrorCode.ACCESS_DENIED, accessDeniedException.getMessage());
    }

    private static void writeResponse(HttpServletResponse response, BaseResponseCode code, String message)
            throws IOException {

        RestResponseEntity responseEntity = RestResponseEntity.builder()
                .code(code)
                .message(code.getMessage())
                .responseData(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseMessage = objectMapper.writeValueAsString(responseEntity);

        response.setStatus(code.getStatus().value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.getWriter().print(responseMessage);
    }
}
