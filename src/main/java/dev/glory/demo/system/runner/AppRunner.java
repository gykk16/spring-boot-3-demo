package dev.glory.demo.system.runner;

import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.util.LogStringUtil;
import dev.glory.demo.system.properties.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppRunner {

	@Value("${info.app.version:}")
	private String appVersion;

	@Bean
    CommandLineRunner run(AppProperties appProperties) {
		return args -> {
			log.info(LogStringUtil.LOG_LINE);
			log.info("# ==> App Name = {}", appProperties.name());
			log.info("# ==> App Version = {}", appVersion);
			log.info(LogStringUtil.LOG_LINE);
		};
	}

}
