package com.planning.occupancy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployeePreference {

    @JsonProperty("employee_id")
    private String employeeId;

    private String name;

    private String team;

    @JsonProperty("desk_preferences")
    private List<String> deskPreferences;

    @JsonProperty("equipment_needs")
    private List<String> equipmentNeeds;

    @JsonProperty("preferred_days")
    private List<String> preferredDays;

    @JsonProperty("preferred_location")
    private String preferredLocation;

    @JsonProperty("accessibility_needs")
    private String accessibilityNeeds;

    @JsonProperty("adjacency_preferences")
    private List<String> adjacencyPreferences;
}
