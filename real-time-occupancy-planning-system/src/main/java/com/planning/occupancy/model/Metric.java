package com.planning.occupancy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Metric {

    @JsonProperty("area_id")
    private String areaId;

    private String date;

    @JsonProperty("peak_occupancy")
    private int peakOccupancy;

    @JsonProperty("average_occupancy")
    private int averageOccupancy;

    @JsonProperty("utilization_rate")
    private double utilizationRate;
}
