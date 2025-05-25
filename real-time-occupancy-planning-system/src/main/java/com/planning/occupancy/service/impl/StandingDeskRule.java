package com.planning.occupancy.service.impl;

import java.util.List;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.service.PolicyRule;

public class StandingDeskRule implements PolicyRule {

    @Override
    public boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        boolean isStanding = "standing".equalsIgnoreCase(desk.getType());
        boolean requestedStanding = "standing".equalsIgnoreCase(query.getDeskType());
        return !(isStanding && !requestedStanding);
    }

}
