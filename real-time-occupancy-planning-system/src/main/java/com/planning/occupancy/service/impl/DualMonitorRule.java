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

public class DualMonitorRule implements PolicyRule {

    @Override
    public boolean validate(Desk desk, StructuredQuery query, OccupancyForecast forecast, Space floor,
            List<Space> floorAreas) {
        boolean deskHasDual = desk.getFeatures().contains("dual-monitors");
        String empId = query.getEmployeeId();
        if (deskHasDual && null != empId) {
            EmployeePreferenceRepository employeePreferenceRepository = new EmployeePreferenceRepository();
            Optional<EmployeePreference> employeePrefOptional = employeePreferenceRepository.findAll().stream()
                    .filter(preference -> preference.getEmployeeId().equalsIgnoreCase(empId))
                    .findFirst();
            if (employeePrefOptional.isPresent()) {
                boolean wantsDual = employeePrefOptional.get().getEquipmentNeeds().contains("dual-monitors");
                return wantsDual;
            }
        }
        return true;
    }
}
