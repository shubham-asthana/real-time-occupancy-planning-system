package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.planning.occupancy.model.Desk;

public class DeskRepository {

    private final List<Desk> desks = DataLoader.getDataLoader().getDesks();

    public List<Desk> findAll() {
        return Collections.unmodifiableList(desks);
    }

    public List<Desk> findByFloorAndType(int floor, String type) {
        return desks.stream()
                .filter(desk -> desk.getFloor() == floor && desk.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Desk> findByArea(String areaId) {
        return desks.stream()
                .filter(desk -> desk.getAreaId().equalsIgnoreCase(areaId))
                .collect(Collectors.toList());
    }
}
