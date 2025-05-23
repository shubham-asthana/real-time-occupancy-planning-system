package com.planning.occupancy.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeWindow {

    private Instant start;
    private Instant end;
}
