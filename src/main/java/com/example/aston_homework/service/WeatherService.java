package com.example.aston_homework.service;

import com.example.aston_homework.exception.ResponseException;
import com.example.aston_homework.model.WeatherDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherDto getWeather(String city){
        try {
            return restTemplate.getForObject(
                    "http://api.weatherstack.com/current?access_key=846227a3a5cd5e2456ae438bac0196e6&query={city}",
                    WeatherDto.class, city);
        }catch (Exception e) {
            throw new ResponseException("Server is unavailable");
        }
    }
}
