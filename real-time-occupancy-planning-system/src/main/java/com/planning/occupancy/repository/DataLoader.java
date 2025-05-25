package com.planning.occupancy.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.planning.occupancy.model.Desk;
import com.planning.occupancy.model.DeskAssignmentRule;
import com.planning.occupancy.model.EmployeePreference;
import com.planning.occupancy.model.Metric;
import com.planning.occupancy.model.OccupancyData;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.Policy;
import com.planning.occupancy.model.Sensor;
import com.planning.occupancy.model.Space;
import com.planning.occupancy.util.JsonUtil;

import lombok.Getter;

@Getter
public class DataLoader {

    private static DataLoader dataLoader;

    private List<Desk> desks;
    private List<Space> spaces;
    private List<Sensor> sensors;
    private List<EmployeePreference> employeePreferences;
    private List<Policy> policies;
    private List<DeskAssignmentRule> deskAssignmentRules;
    private List<Metric> metrics;
    private List<OccupancyData> occupancyData;
    private OccupancyForecast occupancyForecast;

    public static synchronized DataLoader getDataLoader() {
        if (null == dataLoader) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    private DataLoader() {
        loadAll();
    }

    private void loadAll() {
        try {
            InputStream deskStream = getClass().getResourceAsStream("/data/desks.json");
            desks = JsonUtil.fromJson(deskStream, new TypeReference<List<Desk>>() {
            });

            InputStream spaceStream = getClass().getResourceAsStream("/data/spaces.json");
            spaces = JsonUtil.fromJson(spaceStream, new TypeReference<List<Space>>() {
            });

            InputStream sensorStream = getClass().getResourceAsStream("/data/sensors.json");
            sensors = JsonUtil.fromJson(sensorStream, new TypeReference<List<Sensor>>() {
            });

            InputStream occForecastStream = getClass().getResourceAsStream("/data/occupancyForecast.json");
            occupancyForecast = JsonUtil.fromJson(occForecastStream, OccupancyForecast.class);

            InputStream occDataStream = getClass().getResourceAsStream("/data/occupancyData.json");
            occupancyData = JsonUtil.fromJson(occDataStream, new TypeReference<List<OccupancyData>>() {
            });

            InputStream prefStream = getClass().getResourceAsStream("/data/employeePreferences.json");
            employeePreferences = JsonUtil.fromJson(prefStream, new TypeReference<List<EmployeePreference>>() {
            });

            InputStream policyStream = getClass().getResourceAsStream("/data/policies.json");
            policies = JsonUtil.fromJson(policyStream, new TypeReference<List<Policy>>() {
            });

            InputStream ruleStream = getClass().getResourceAsStream("/data/deskAssignmentRules.json");
            deskAssignmentRules = JsonUtil.fromJson(ruleStream, new TypeReference<List<DeskAssignmentRule>>() {
            });

            InputStream metricStream = getClass().getResourceAsStream("/data/metrics.json");
            metrics = JsonUtil.fromJson(metricStream, new TypeReference<List<Metric>>() {
            });

        } catch (IOException ex) {
            throw new RuntimeException("Failed to load JSON data", ex);
        }
    }
}
