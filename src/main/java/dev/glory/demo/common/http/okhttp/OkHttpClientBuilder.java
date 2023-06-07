package dev.glory.demo.common.http.okhttp;

import static java.util.concurrent.TimeUnit.SECONDS;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.http.HttpProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OkHttpClientBuilder {

    private final HttpProperties         httpProperties;
    private final OkHttpClient           defaultOkHttpClient;
    private final ConnectionPool         defaultConnectionPool;
    private final HttpLoggingInterceptor loggingInterceptor;


    public OkHttpClientBuilder(HttpProperties httpProperties) {
        this.httpProperties = httpProperties;
        this.loggingInterceptor = createLoggingInterceptor();
        this.defaultConnectionPool = createDefaultConnectionPool();
        this.defaultOkHttpClient = createDefaultOkHttpClient();
    }

    /**
     * 기본 OkHttpClient 반환
     *
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        return defaultOkHttpClient;
    }

    /**
     * 신규 OkHttpClient 생성
     *
     * @param config OkHttpClient 설정 정보
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient(@Nonnull OkHttpBuilderConfig config) {
        return defaultOkHttpClient.newBuilder()
                .connectTimeout(config.getConnectTimeoutSec(), SECONDS)
                .writeTimeout(config.getWriteTimeoutSec(), SECONDS)
                .readTimeout(config.getReadTimeoutSec(), SECONDS)
                .callTimeout(config.getCallTimeoutSec(), SECONDS)
                .build();
    }

    /**
     * 기본 OkHttpClient 반환
     */
    private OkHttpClient createDefaultOkHttpClient() {
        log.debug("==> OkHttpClientBuilder.createDefaultOkHttpClient");

        return new OkHttpClient.Builder()
                .connectionPool(defaultConnectionPool)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(httpProperties.connectTimeoutSec(), SECONDS)
                .writeTimeout(httpProperties.writeTimeoutSec(), SECONDS)
                .readTimeout(httpProperties.readTimeoutSec(), SECONDS)
                .callTimeout(httpProperties.callTimeoutSec(), SECONDS)
                .build();
    }

    /**
     * 기본 커넥션 풀 생성
     */
    private ConnectionPool createDefaultConnectionPool() {
        log.debug("==> OkHttpClientBuilder.createDefaultConnectionPool");

        return new ConnectionPool(httpProperties.maxIdleConnectionsSec(), httpProperties.keepAliveDurationSec(),
                SECONDS);
    }

    /**
     * 로깅 인터셉터 생성
     */
    private HttpLoggingInterceptor createLoggingInterceptor() {
        log.debug("==> OkHttpClientBuilder.createLoggingInterceptor");

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        if (log.isDebugEnabled()) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return httpLoggingInterceptor;
    }

    /**
     * 커넥션 풀 종료
     */
    @PreDestroy
    public void destroy() {
        log.debug("# ==> OkHttpClientBuilder.destroy");
        defaultConnectionPool.evictAll();
    }

}
