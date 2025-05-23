package com.planning.occupancy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Space {

    private String id;

    private String name;

    private String type;

    private int capacity;

    @JsonProperty("parent_id")
    private String parentId;
}
