package com.example.aston_homework.service;

import com.example.aston_homework.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class WeatherService {

    private RestTemplate restTemplate = new RestTemplate();

    public Weather getWeather(String city){
        try {
            return restTemplate.getForObject(
                    "http://api.weatherstack.com/current?access_key=846227a3a5cd5e2456ae438bac0196e6&query={city}",
                    Weather.class, city);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
