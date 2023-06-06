package dev.glory.demo.system.runner;

import lombok.extern.slf4j.Slf4j;

import dev.glory.demo.common.util.LogStringUtil;
import dev.glory.demo.system.config.security.role.Role;
import dev.glory.demo.system.properties.AppProperties;
import dev.glory.demo.web.apicontroller.auth.dto.AuthenticationResponseDTO;
import dev.glory.demo.web.apicontroller.auth.dto.RegisterRequestDTO;
import dev.glory.demo.web.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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

    @Bean
    @Profile("local")
    CommandLineRunner runLocal(AuthService authService) {
        return args -> {

            RegisterRequestDTO userManager =
                    new RegisterRequestDTO("manager", "password", "manager", "manager@demo.com", Role.MANAGER);
            RegisterRequestDTO userAdmin =
                    new RegisterRequestDTO("admin", "password", "admin", "admin@demo.com", Role.ADMIN);

            AuthenticationResponseDTO managerAuth = authService.register(userManager);
            AuthenticationResponseDTO adminAuth = authService.register(userAdmin);

            log.info("==> adminAuth.getAccessToken() = {}", adminAuth.getAccessToken());
            log.info("==> managerAuth.getAccessToken() = {}", managerAuth.getAccessToken());
        };
    }

}
