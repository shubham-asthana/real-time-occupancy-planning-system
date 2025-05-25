package com.planning.occupancy.service;

import com.planning.occupancy.model.TimeWindow;

public interface MetricService {

    default double getOccupancyTrend(String areaId, TimeWindow window) {
        throw new UnsupportedOperationException();
    }

    default double getUtilizationRate(String areaId) {
        throw new UnsupportedOperationException();
    }
}
