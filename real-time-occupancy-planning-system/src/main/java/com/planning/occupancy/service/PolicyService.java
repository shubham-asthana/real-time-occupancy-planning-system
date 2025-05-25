package com.planning.occupancy.service;

import java.util.Arrays;
import java.util.List;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.service.impl.AdjacenyRule;
import com.planning.occupancy.service.impl.CapacityLimitRule;
import com.planning.occupancy.service.impl.DualMonitorRule;
import com.planning.occupancy.service.impl.SantizationRule;
import com.planning.occupancy.service.impl.StandingDeskRule;

public class PolicyService {

    private final List<PolicyRule> rules = Arrays.asList(
            new SantizationRule(),
            new StandingDeskRule(),
            new DualMonitorRule(),
            new AdjacenyRule());

    public boolean isDeskValid(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        for (PolicyRule rule : rules) {
            if (!rule.validate(desk, query, forecast, floor, floorAreas)) {
                return false;
            }
        }
        return true;
    }
}
