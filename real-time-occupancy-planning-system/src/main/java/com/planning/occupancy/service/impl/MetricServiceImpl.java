package com.planning.occupancy.service.impl;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.planning.occupancy.model.Metric;
import com.planning.occupancy.model.OccupancyForecast;
import com.planning.occupancy.model.TimeWindow;
import com.planning.occupancy.repository.MetricsRepository;
import com.planning.occupancy.repository.OccupancyRepository;
import com.planning.occupancy.service.MetricService;

public class MetricServiceImpl implements MetricService {

    private final OccupancyRepository occupancyRepository = new OccupancyRepository();
    private final List<Metric> metrics = new MetricsRepository().findAll();

    @Override
    public double getOccupancyTrend(String areaId, TimeWindow window) {
        OccupancyForecast forecast = occupancyRepository.getOccupancyForecast();
        OccupancyForecast.NextDayForecast nextDayForecast = forecast.getNextDay().get(areaId);
        if (null == nextDayForecast)
            return 0.0;

        LocalTime startLt = window.getStart()
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.of("Asia/Kolkata"))
                .toLocalTime();

        if (startLt.isBefore(LocalTime.NOON)) {
            return nextDayForecast.getMorning() / 100.0;
        } else if (startLt.isBefore(LocalTime.of(18, 0))) {
            return nextDayForecast.getAfternoon() / 100.0;
        } else {
            return nextDayForecast.getEvening() / 100.0;
        }
    }

    @Override
    public double getUtilizationRate(String areaId) {
        Optional<Metric> latest = metrics.stream()
                .filter(metric -> metric.getAreaId().equals(areaId))
                .max(Comparator.comparing(Metric::getDate));
        return latest.map(Metric::getUtilizationRate).orElse(0.0);
    }
}
