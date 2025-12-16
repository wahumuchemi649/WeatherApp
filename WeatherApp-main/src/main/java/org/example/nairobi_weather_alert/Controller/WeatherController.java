package org.example.nairobi_weather_alert.Controller;

import org.example.nairobi_weather_alert.Model.CountyListResponse;
import org.example.nairobi_weather_alert.Model.WeatherResponse;
import org.example.nairobi_weather_alert.Service.WeatherService;
import org.example.nairobi_weather_alert.Weatherapp.Counties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Weather REST Controller
 * Handles all weather-related API endpoints
 */
@RestController
@RequestMapping("/api")  // All endpoints start with /api
@CrossOrigin(origins = "*")  // Allow React frontend to call these APIs
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    // Constructor injection (best practice)
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Get list of all Kenyan counties
     * GET /api/counties
     */
    @GetMapping("/counties")
    public ResponseEntity<CountyListResponse> getCounties() {
        logger.info("Fetching list of Kenyan counties");
        CountyListResponse response = new CountyListResponse(Counties.KENYA_COUNTIES);
        return ResponseEntity.ok(response);
    }

    /**
     * Get weather data for a specific county
     * GET /api/weather/{county}
     *
     */
    @GetMapping("/weather/{county}")
    public ResponseEntity<?> getWeatherByCounty(@PathVariable String county) {
        logger.info("Received request for weather data: {}", county);

        try {
            // Validate county exists in Kenya
            if (!Counties.KENYA_COUNTIES.contains(county)) {
                logger.warn("Invalid county requested: {}", county);
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(
                                "Invalid county name. Please use one of the 47 Kenyan counties.",
                                "Available counties: " + String.join(", ", Counties.KENYA_COUNTIES)
                        ));
            }

            // Get weather data (will use cache if available)
            WeatherResponse weather = weatherService.getWeatherByCounty(county);
            logger.info("Successfully retrieved weather for {}: {}°C", county, weather.getTemperature());

            return ResponseEntity.ok(weather);

        } catch (Exception e) {
            logger.error("Error processing weather request for {}: {}", county, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            "Failed to fetch weather data",
                            e.getMessage()
                    ));
        }
    }

    /**
     * Health check endpoint
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Weather API is running!");
    }

    /**
     * Error Response class for structured error messages
     */
    private static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }
}
