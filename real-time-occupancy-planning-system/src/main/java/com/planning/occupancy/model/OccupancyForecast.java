package com.planning.occupancy.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OccupancyForecast {

    @JsonProperty("forecast")
    private Map<String, NextDayForecast> nextDay;

    @Data
    public static class NextDayForecast {

        private int morning;

        private int afternoon;

        private int evening;
    }
}
