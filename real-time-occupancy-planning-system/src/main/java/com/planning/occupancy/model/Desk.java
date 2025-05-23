package com.planning.occupancy.model;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Desk {

    private String id;

    private String type;

    @JsonProperty("area_id")
    private String areaId;

    private int floor;

    private String zone;

    @JsonProperty("location_description")
    private String locationDescription;

    private List<String> features;

    private String status;

    @JsonProperty("last_used")
    private Instant lastUsed;
}
