package com.vinteo.inventory.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt.refresh")

@Setter
@Getter
public class PropertiesConfiguration {
    private int time = 5;

}