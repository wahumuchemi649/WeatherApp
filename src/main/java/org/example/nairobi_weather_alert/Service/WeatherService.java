package org.example.nairobi_weather_alert.Service;

import org.example.nairobi_weather_alert.Model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;
import java.util.List;

@Service
public class WeatherService {
    private static final String API_KEY ="109343f037d763c6ea1f8b362a718c20";

    public static WeatherResponse getWeatherByCounty(String county){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + county + "&appid=" + API_KEY + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        double temp = (double) main.get("temp");

        Map<String, Object> weather = ((List<Map<String, Object>>) response.get("weather")).get(0);
        String condition = (String) weather.get("main");

        // Alerts are optional; you can leave as null if using simple current weather API
        String alert = null;

        return new WeatherResponse(temp, condition, alert);

    }
}
