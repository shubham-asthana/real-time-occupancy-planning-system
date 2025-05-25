package com.planning.occupancy.service.impl;

import java.util.List;
import java.util.Optional;

import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.EmployeePreference;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.model.StructuredQuery;
import com.planning.occupancy.repository.EmployeePreferenceRepository;
import com.planning.occupancy.service.PolicyRule;

public class AdjacenyRule implements PolicyRule {

    @Override
    public boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        String empId = query.getEmployeeId();
        if (null == empId) {
            return true;
        }

        EmployeePreferenceRepository employeePreferenceRepository = new EmployeePreferenceRepository();
        Optional<EmployeePreference> employeePrefOptional = employeePreferenceRepository.findAll().stream()
                .filter(preference -> preference.getEmployeeId().equalsIgnoreCase(empId))
                .findFirst();

        if (!employeePrefOptional.isPresent()) {
            return true;
        }

        EmployeePreference employeePreference = employeePrefOptional.get();
        List<String> adjacencyPreferences = employeePreference.getAdjacencyPreferences();
        if (null == adjacencyPreferences || adjacencyPreferences.isEmpty()) {
            return true;
        }

        for (String adjacencyPreference : adjacencyPreferences) {
            if (desk.getZone().equalsIgnoreCase("Marketing Zone")
                    && adjacencyPreference.equalsIgnoreCase("marketing-team")) {
                return true;
            }
        }
        return false;
    }
}
