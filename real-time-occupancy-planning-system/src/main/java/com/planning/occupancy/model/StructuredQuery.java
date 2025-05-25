package com.planning.occupancy.model;

import lombok.Data;

@Data
public class StructuredQuery {

    private int floor;
    private String deskType;
    private String teamZone;
    private TimeWindow timeWindow;
    private String employeeId;
}
