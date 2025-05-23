package com.planning.occupancy.repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.planning.occupancy.model.EmployeePreference;

public class EmployeePreferenceRepository {
    private final List<EmployeePreference> employeePreferences = DataLoader.getDataLoader().getEmployeePreferences();

    public List<EmployeePreference> findAll() {
        return Collections.unmodifiableList(employeePreferences);
    }

    public List<EmployeePreference> findByTeam(String team) {
        return employeePreferences.stream()
                .filter(employeePreference -> employeePreference.getTeam().equalsIgnoreCase(team))
                .collect(Collectors.toList());
    }
}
