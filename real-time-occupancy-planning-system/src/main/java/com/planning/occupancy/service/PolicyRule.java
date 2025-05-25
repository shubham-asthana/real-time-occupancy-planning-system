package com.planning.occupancy.service;

import java.util.List;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;

public interface PolicyRule {

    boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor, List<Space> floorAreas);
}
