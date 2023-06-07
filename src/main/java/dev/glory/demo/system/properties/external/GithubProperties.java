package dev.glory.demo.system.properties.external;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.github")
public record GithubProperties(String hostUrl, String users) {

}
