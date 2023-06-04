package dev.glory.demo.system.config;

import java.util.List;

import lombok.RequiredArgsConstructor;

import dev.glory.demo.system.interceptor.InfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final InfoInterceptor infoInterceptor;

    private static final List<String> INTERCEPTOR_EXCLUDE_LIST = List.of(
            "/css/**", "/js/**", "/img/**", "/images/**", "/error/**", "/download/**", "/common/file**", "/*.ico"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(infoInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(INTERCEPTOR_EXCLUDE_LIST);
    }
}
