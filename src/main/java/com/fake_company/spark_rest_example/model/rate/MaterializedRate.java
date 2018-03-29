package com.fake_company.spark_rest_example.model.rate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaterializedRate {
    private LocalTime startTime;
    private LocalTime endTime;
    private List<DayOfWeek> daysApplied;
    private Integer price;

    public MaterializedRate(final LocalTime startTime, final LocalTime endTIme,
                            final List<DayOfWeek> daysApplied, final Integer price) {
        this.startTime = startTime;
        this.endTime = endTIme;
        this.daysApplied = daysApplied;
        this.price = price;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public List<DayOfWeek> getDaysApplied() {
        return daysApplied;
    }

    public Integer getPrice() {
        return price;
    }

    public boolean isWithinRate(final ZonedDateTime start, final ZonedDateTime end) {
        if(daysApplied.stream().anyMatch(d -> d == start.getDayOfWeek())) {
            return LocalTime.of(start.getHour(), start.getMinute()).compareTo(startTime) >= 0
            && LocalTime.of(end.getHour(), end.getMinute()).compareTo(endTime) <= 0;
        }
        return false;
    }

    public static MaterializedRate fromRate(final Rate rate) {
        final String[] times = rate.getTimes().split("-");
        final LocalTime startTime = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HHMM"));
        final LocalTime endTime = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HHMM"));
        final Integer price = rate.getPrice();
        final List<DayOfWeek> daysApplied = Stream.of(rate.getDays().split(","))
                .map(WeekDayMapper::valueOf)
                .map(o -> o.day)
                .collect(Collectors.toList());
        return new MaterializedRate(startTime, endTime, daysApplied, price);
    }
}
