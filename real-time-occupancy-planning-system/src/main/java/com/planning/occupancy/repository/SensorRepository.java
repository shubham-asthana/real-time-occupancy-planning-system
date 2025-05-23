package com.planning.occupancy.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.planning.occupancy.model.Sensor;

public class SensorRepository {
    private final List<Sensor> sensors = DataLoader.getDataLoader().getSensors();

    public List<Sensor> findByArea(String areaId) {
        return sensors.stream()
                .filter(sensor -> sensor.getAreaId().equals(areaId))
                .collect(Collectors.toList());
    }

    public boolean isSensorActive(String areaId) {
        return sensors.stream()
                .anyMatch(sensor -> sensor.getAreaId().equals(areaId) && sensor.getStatus().equalsIgnoreCase("active"));
    }
}
