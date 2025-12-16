package org.example.nairobi_weather_alert.Model;


import lombok.Data;

/**
 * Enhanced Weather Response Model
 * Contains all relevant weather information
 */
@Data
public class WeatherResponse {

    private double temperature;
    private double feelsLike;
    private int humidity;
    private double windSpeed;
    private String conditions;
    private String description;
    private String alerts;

    // Default constructor
    public WeatherResponse() {
    }

    // Constructor with basic fields
    public WeatherResponse(double temperature, String conditions, String alerts) {
        this.temperature = temperature;
        this.conditions = conditions;
        this.alerts = alerts;
    }

    // Constructor with all fields
    public WeatherResponse(double temperature, String conditions, String description,
                           String alerts, double feelsLike, int humidity, double windSpeed) {
        this.temperature = temperature;
        this.conditions = conditions;
        this.description = description;
        this.alerts = alerts;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }



    @Override
    public String toString() {
        return "WeatherResponse{" +
                "temperature=" + temperature +
                ", feelsLike=" + feelsLike +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", conditions='" + conditions + '\'' +
                ", description='" + description + '\'' +
                ", alerts='" + alerts + '\'' +
                '}';
    }
}
