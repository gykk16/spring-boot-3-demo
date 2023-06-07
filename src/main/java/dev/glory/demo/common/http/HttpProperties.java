package dev.glory.demo.common.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 기본 Http 설정
 *
 * @param maxIdleConnectionsSec
 * @param keepAliveDurationSec
 * @param connectTimeoutSec
 * @param writeTimeoutSec
 * @param readTimeoutSec
 * @param callTimeoutSec
 */
@ConfigurationProperties(prefix = "application.http")
public record HttpProperties(int maxIdleConnectionsSec,
                             int keepAliveDurationSec,
                             long connectTimeoutSec,
                             long writeTimeoutSec,
                             long readTimeoutSec,
                             long callTimeoutSec) {

}
