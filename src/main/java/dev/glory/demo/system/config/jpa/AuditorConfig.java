package dev.glory.demo.system.config.jpa;


import java.util.Optional;

import dev.glory.demo.common.constant.ApplicationConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * AuditorProvider
 */
@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(ApplicationConst.getMdcAccessId().orElse("unknown"));
    }
}
