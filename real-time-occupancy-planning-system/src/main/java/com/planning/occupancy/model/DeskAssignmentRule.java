package com.planning.occupancy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeskAssignmentRule {

    @JsonProperty("rule_id")
    private String ruleId;

    private String description;

    private int priority;
}
