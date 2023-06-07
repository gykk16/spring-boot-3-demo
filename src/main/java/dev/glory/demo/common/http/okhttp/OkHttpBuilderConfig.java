package dev.glory.demo.common.http.okhttp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OkHttpBuilderConfig {

    private Long connectTimeoutSec;
    private Long writeTimeoutSec;
    private Long readTimeoutSec;
    private Long callTimeoutSec;

    public static OkHttpBuilderConfig buildThreeSecConfig() {
        return new OkHttpBuilderConfig(3L, 3L, 3L, 3L);
    }

    public static OkHttpBuilderConfig buildSevenSecConfig() {
        return new OkHttpBuilderConfig(7L, 7L, 7L, 7L);
    }

    public static OkHttpBuilderConfig buildTenSecConfig() {
        return new OkHttpBuilderConfig(10L, 10L, 10L, 10L);
    }

    public static OkHttpBuilderConfig buildCustomConfig(long connectTimeoutSec, long writeTimeoutSec, long readTimeoutSec, long callTimeoutSec) {
        return new OkHttpBuilderConfig(connectTimeoutSec, writeTimeoutSec, readTimeoutSec, callTimeoutSec);
    }

}
