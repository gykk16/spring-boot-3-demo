package dev.glory.demo.system.config.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.security.jwt")
public record JwtProperties(long accessTokenExpireMin, long refreshTokenExpireMin) {

}
