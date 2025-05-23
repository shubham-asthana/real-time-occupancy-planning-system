package com.planning.occupancy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Policy {

    private String id;

    private String name;

    private String description;

    private boolean active;

    @JsonProperty("enforcement_level")
    private String enforcementLevel;
}
