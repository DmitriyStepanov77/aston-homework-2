package com.example.aston_homework.model;

import lombok.Data;
import lombok.experimental.Delegate;

import java.time.LocalDateTime;
@Data
public class Weather {

    @Delegate
    private Request request;
    @Delegate
    private Location location;
    @Delegate
    private Current current;
    @Data
    static class Request{
        private String type;
        private String query;
        private String language;
    }
    @Data
    static class Location{
        private String country;
        private String region;
    }
    @Data
    static class Current{
        private int temperature;
        private int wind_speed;
        private int wind_degree;
    }
}
