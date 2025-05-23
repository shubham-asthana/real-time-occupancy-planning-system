package com.planning.occupancy.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OccupancyData {

    @JsonProperty("area_id")
    private String areaId;

    private Instant timestamp;

    @JsonProperty("occupancy_count")
    private int occupancyCount;

    @JsonProperty("occupancy_percentage")
    private int occupancyPercentage;
}
