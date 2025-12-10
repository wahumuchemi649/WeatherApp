package org.example.nairobi_weather_alert.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.nairobi_weather_alert.Service.WeatherService;
import org.example.nairobi_weather_alert.Model.WeatherResponse;
import org.example.nairobi_weather_alert.Model.CountyListResponse;
import org.example.nairobi_weather_alert.Weatherapp.Counties;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping("/counties")
    public CountyListResponse getCounties(){
        return new CountyListResponse(Counties.KENYA_COUNTIES);
    }
    @GetMapping("/weather")
    public WeatherResponse getWeather(@RequestParam String county){

        return  WeatherService.getWeatherByCounty(county);
    }

}
