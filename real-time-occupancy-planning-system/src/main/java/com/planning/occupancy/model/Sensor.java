package com.planning.occupancy.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Sensor {

    private String id;

    private String type;

    private String status;

    @JsonProperty("area_id")
    private String areaId;

    @JsonProperty("last_reading")
    private Instant lastReading;
}
