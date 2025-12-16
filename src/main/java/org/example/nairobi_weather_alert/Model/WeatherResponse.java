package org.example.nairobi_weather_alert.Model;

public class WeatherResponse {

    public double temperature;
    public String conditions;
    public String alerts; //optional

    public WeatherResponse(double temperature, String conditions, String alerts) {
        this.temperature = temperature;
        this.conditions = conditions;
        this.alerts = alerts;
    }
    //Getters and Setters


    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public String getAlerts() {
        return alerts;
    }
    public void setAlerts(String alerts) {
        this.alerts = alerts;
    }
}
