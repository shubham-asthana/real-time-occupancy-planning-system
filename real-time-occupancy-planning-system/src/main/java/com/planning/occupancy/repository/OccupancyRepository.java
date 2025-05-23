package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;

import com.planning.occupancy.model.OccupancyData;
import com.planning.occupancy.model.OccupancyForecast;

public class OccupancyRepository {

    private final List<OccupancyData> occupancyData = DataLoader.getDataLoader().getOccupancyData();
    private final OccupancyForecast occupancyForecast = DataLoader.getDataLoader().getOccupancyForecast();

    public List<OccupancyData> getOccupancyData() {
        return Collections.unmodifiableList(occupancyData);
    }

    public OccupancyForecast getOccupancyForecast() {
        return occupancyForecast;
    }
}
