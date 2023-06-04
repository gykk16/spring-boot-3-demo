package dev.glory.demo.system.config.security.jwt.exception;

import lombok.Getter;

import org.springframework.security.core.AuthenticationException;

@Getter
public class CustomJwtException extends AuthenticationException {

    private final TokenCode code;

    public CustomJwtException(TokenCode code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public CustomJwtException(TokenCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomJwtException(TokenCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
