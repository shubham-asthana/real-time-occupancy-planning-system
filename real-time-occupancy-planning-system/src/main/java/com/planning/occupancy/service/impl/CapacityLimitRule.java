package com.planning.occupancy.service.impl;

import java.util.List;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.service.PolicyRule;

public class CapacityLimitRule implements PolicyRule {

    @Override
    public boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        int totalCapacity = floor.getCapacity();
        int occSum = 0;
        for (Space area : floorAreas) {
            OccupancyForecast.NextDayForecast nextDayForecast = forecast.getNextDay().get(area.getId());
            if (null != nextDayForecast) {
                occSum += nextDayForecast.getAfternoon();
            }
        }
        double occupancyRatio = (double) occSum / totalCapacity;
        return occupancyRatio < 0.80;
    }

}
