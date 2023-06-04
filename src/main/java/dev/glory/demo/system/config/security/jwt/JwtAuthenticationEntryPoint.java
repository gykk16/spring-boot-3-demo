package dev.glory.demo.system.config.security.jwt;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.glory.demo.common.RestResponseEntity;
import dev.glory.demo.common.code.BaseResponseCode;
import dev.glory.demo.system.config.security.jwt.exception.CustomJwtException;
import dev.glory.demo.system.config.security.jwt.exception.TokenCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        Exception exception = (Exception)request.getAttribute("exception");

        if (exception instanceof CustomJwtException customJwtException) {
            writeResponse(response, customJwtException.getCode(), customJwtException.getMessage());
            return;
        }

        writeResponse(response, TokenCode.TOKEN_ERROR, authException.getMessage());
    }

    private static void writeResponse(HttpServletResponse response, BaseResponseCode code, String message)
            throws IOException {

        RestResponseEntity responseEntity = RestResponseEntity.builder()
                .code(code)
                .message(message)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String responseMessage = objectMapper.writeValueAsString(responseEntity);

        response.setStatus(code.getStatus().value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.getWriter().print(responseMessage);
    }
}
