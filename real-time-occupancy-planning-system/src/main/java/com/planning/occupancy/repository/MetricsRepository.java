package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;

import com.planning.occupancy.model.Metric;

public class MetricsRepository {

    private final List<Metric> metrics = DataLoader.getDataLoader().getMetrics();

    public List<Metric> findAll() {
        return Collections.unmodifiableList(metrics);
    }
}
