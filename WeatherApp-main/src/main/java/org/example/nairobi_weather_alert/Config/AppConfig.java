package org.example.nairobi_weather_alert.Config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Application Configuration
 * Define beans that will be used throughout the application
 */
@Configuration
public class AppConfig {

    /**
     * RestTemplate Bean for making HTTP calls
     * This is injected into services that need to call external APIs
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))  // Connection timeout
                .setReadTimeout(Duration.ofSeconds(10))     // Read timeout
                .build();
    }
}