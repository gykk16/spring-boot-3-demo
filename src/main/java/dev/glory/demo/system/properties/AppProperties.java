package dev.glory.demo.system.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "info.app")
public record AppProperties(String name, String nameKor, String group, String alias) {

}
