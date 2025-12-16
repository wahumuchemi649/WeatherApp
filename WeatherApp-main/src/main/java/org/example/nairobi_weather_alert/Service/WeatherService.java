package org.example.nairobi_weather_alert.Service;

import org.example.nairobi_weather_alert.Model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

/**
 * Weather Service with Caching
 * The @Cacheable annotation ensures weather data is cached for 30 minutes
 */
@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    // Inject API key from application.properties
    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    // Constructor injection (Best practice)
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get weather data by county with caching
     *
     * @Cacheable annotation does the magic:
     * - First call: Fetches from API and stores in cache
     * - Subsequent calls: Returns from cache (no API call)
     * - Cache expires after 30 minutes (configured in CacheConfig)
     *
     * The "key" parameter ensures each county has its own cache entry
     */
    @Cacheable(value = "weatherCache", key = "#county.toLowerCase()")
    public WeatherResponse getWeatherByCounty(String county) {
        logger.info("Fetching weather data for county: {} (This means cache miss or expired)", county);

        try {
            // Build the API URL
            String url = String.format("%s?q=%s,KE&appid=%s&units=metric",
                    apiUrl, county, apiKey);

            logger.debug("Calling OpenWeatherMap API: {}", url.replace(apiKey, "***"));

            // Make API call
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                throw new RuntimeException("Empty response from weather API");
            }

            // Extract temperature
            Map<String, Object> main = (Map<String, Object>) response.get("main");
            double temp = ((Number) main.get("temp")).doubleValue();
            double feelsLike = ((Number) main.get("feels_like")).doubleValue();
            int humidity = ((Number) main.get("humidity")).intValue();

            // Extract weather conditions
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            Map<String, Object> weather = weatherList.get(0);
            String condition = (String) weather.get("main");
            String description = (String) weather.get("description");

            // Extract wind data
            Map<String, Object> wind = (Map<String, Object>) response.get("wind");
            double windSpeed = wind != null ? ((Number) wind.get("speed")).doubleValue() : 0.0;

            // Generate alert based on weather conditions
            String alert = generateAlert(temp, condition, windSpeed);

            logger.info("Weather data successfully retrieved for {}: {}°C, {}",
                    county, temp, condition);

            return new WeatherResponse(
                    temp,
                    condition,
                    description,
                    alert,
                    feelsLike,
                    humidity,
                    windSpeed
            );

        } catch (RestClientException e) {
            logger.error("Error fetching weather data for county: {}", county, e);
            throw new RuntimeException("Failed to fetch weather data for " + county +
                    ". Please check if the county name is correct.", e);
        }
    }

    /**
     * Generate weather alerts based on conditions
     */
    private String generateAlert(double temp, String condition, double windSpeed) {
        if (temp > 35) {
            return "HEAT ALERT: Very high temperature! Stay hydrated.";
        } else if (temp < 10) {
            return "COLD ALERT: Low temperature expected. Dress warmly.";
        } else if (condition.equalsIgnoreCase("Rain") || condition.equalsIgnoreCase("Thunderstorm")) {
            return "RAIN ALERT: Rainfall expected. Carry an umbrella.";
        } else if (windSpeed > 10) {
            return "WIND ALERT: Strong winds expected.";
        } else if (condition.equalsIgnoreCase("Clear")) {
            return "Perfect weather! Enjoy your day.";
        }
        return null;
    }
}