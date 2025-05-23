package com.planning.occupancy.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.repository.DeskAssignmentRuleRepository;
import com.planning.occupancy.repository.DeskRepository;
import com.planning.occupancy.repository.EmployeePreferenceRepository;
import com.planning.occupancy.repository.OccupancyRepository;
import com.planning.occupancy.repository.PolicyRepository;
import com.planning.occupancy.repository.SensorRepository;
import com.planning.occupancy.repository.SpaceRepository;

public class DeskPlanner {
    private final DeskRepository deskRepository = new DeskRepository();
    private final SpaceRepository spaceRepository = new SpaceRepository();
    private final OccupancyRepository occupancyRepository = new OccupancyRepository();
    private final SensorRepository sensorRepository = new SensorRepository();
    private final EmployeePreferenceRepository employeePreferenceRepository = new EmployeePreferenceRepository();
    private final PolicyRepository policyRepository = new PolicyRepository();
    private final DeskAssignmentRuleRepository deskAssignmentRuleRepository = new DeskAssignmentRuleRepository();

    public List<Desk> findDesks(StructuredQuery query) {
        Optional<Space> spaceOptional = spaceRepository.findByName(query.getFloor() + "rd Floor");
        if (!spaceOptional.isPresent())
            return Collections.emptyList();
        Space space = spaceOptional.get();

        List<Space> zones = spaceRepository.findByParentId(space.getId());
        Space marketingZone = zones.stream()
                .filter(zone -> zone.getName().equalsIgnoreCase(query.getTeamZone()))
                .findFirst()
                .orElse(null);

        if (null == marketingZone)
            return Collections.emptyList();

        List<Space> marketingArea = spaceRepository.findByParentId(marketingZone.getId());

        List<Desk> desks = deskRepository.findByFloorAndType(query.getFloor(), query.getDeskType());
        desks = desks.stream()
                .filter(desk -> marketingArea.stream()
                        .anyMatch(area -> area.getId().equalsIgnoreCase(desk.getAreaId())))
                .collect(Collectors.toList());

        desks = desks.stream()
                .filter(desk -> sensorRepository.isSensorActive(desk.getAreaId()))
                .collect(Collectors.toList());

        desks = desks.stream()
                .filter(desk -> {
                    OccupancyForecast.NextDayForecast nextDayForecast = occupancyRepository.getOccupancyForecast()
                            .getNextDay().get(desk.getAreaId());
                    return null != nextDayForecast && nextDayForecast.getAfternoon() < 100;
                })
                .collect(Collectors.toList());

        return desks;
    }
}
