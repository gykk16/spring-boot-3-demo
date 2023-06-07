package dev.glory.demo.common.http.retrofit;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.http.okhttp.OkHttpBuilderConfig;
import dev.glory.demo.common.http.okhttp.OkHttpClientBuilder;
import okhttp3.OkHttpClient;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Retrofit Service Generator
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RetrofitServiceGenerator {

    private final OkHttpClientBuilder okHttpClientBuilder;


    /**
     * 기본 Retrofit Service 생성
     *
     * @param baseUrl      url
     * @param serviceClass Retrofit 서비스 클래스
     * @return -
     */
    public <S> S createService(String baseUrl, Class<S> serviceClass) {
        return createService(baseUrl, serviceClass, JacksonConverterType.JSON);
    }

    /**
     * @param baseUrl       url
     * @param serviceClass  Retrofit 서비스 클래스
     * @param converterType JacksonConverterType
     * @return -
     */
    public <S> S createService(String baseUrl, Class<S> serviceClass, JacksonConverterType converterType) {
        return createService(baseUrl, serviceClass, converterType, null);
    }

    /**
     * Retrofit Service 생성
     *
     * @param baseUrl       url
     * @param serviceClass  Retrofit 서비스 클래스
     * @param converterType JacksonConverterType
     * @param config        OkHttpBuilderConfig
     * @return -
     */
    public <S> S createService(String baseUrl, Class<S> serviceClass, JacksonConverterType converterType,
            OkHttpBuilderConfig config) {

        OkHttpClient okHttpClient = config == null
                ? okHttpClientBuilder.getOkHttpClient() : okHttpClientBuilder.getOkHttpClient(config);

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(converterType.getConverter())
                .build()
                .create(serviceClass);
    }


    /**
     * Jackson Converter Type
     */
    public enum JacksonConverterType {
        JSON, XML;

        /**
         * JacksonConverterFactory 반환
         */
        public JacksonConverterFactory getConverter() {
            return switch (this) {
                case JSON -> JacksonConverterFactory.create(Jackson2ObjectMapperBuilder.json().build());
                case XML -> JacksonConverterFactory.create(Jackson2ObjectMapperBuilder.xml().build());
                default -> JacksonConverterFactory.create(Jackson2ObjectMapperBuilder.json().build());
            };
        }
    }
}
