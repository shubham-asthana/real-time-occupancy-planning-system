package com.planning.occupancy.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.service.PolicyRule;

public class SantizationRule implements PolicyRule {

    @Override
    public boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        Instant lastUsed = desk.getLastUsed();
        if (null == lastUsed)
            return true;
        Instant threshold = Instant.now().minus(4, ChronoUnit.HOURS);
        return lastUsed.isBefore(threshold);
    }

}
