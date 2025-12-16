package org.example.nairobi_weather_alert.Config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache Configuration for Weather Data
 * This ensures we don't hit the OpenWeatherMap API too frequently
 */
@Configuration
@EnableCaching  // This enables Spring's caching functionality
public class CacheConfig {

    /**
     * Configure Caffeine Cache Manager
     * Weather data will be cached for 30 minutes to reduce API calls
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherCache");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    /**
     * Caffeine Cache Builder with custom settings
     * - Maximum 100 entries in cache
     * - Expire after 30 minutes
     * - Record statistics for monitoring
     */
    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(30, TimeUnit.MINUTES)  // Cache for 30 minutes
                .recordStats();  // Enable cache statistics
    }
}
