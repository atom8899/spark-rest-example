package com.fake_company.spark_rest_example.model.rate;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaterializedRate {
    enum DateMapping {
        mon("mon", DayOfWeek.MONDAY),
        tues("tues", DayOfWeek.TUESDAY),
        wed("wed", DayOfWeek.WEDNESDAY),
        thurs("thurs", DayOfWeek.THURSDAY),
        fri("fri", DayOfWeek.FRIDAY),
        sat("sat", DayOfWeek.SATURDAY),
        sun("sun", DayOfWeek.SUNDAY);

        private String value;
        private DayOfWeek day;

        DateMapping(final String value, final DayOfWeek day) {
            this.value = value;
            this.day = day;
        }

        public String getValue() {
            return value;
        }

        public Optional<DateMapping> parse(final String input) {
            for (DateMapping dateMapping : DateMapping.values()) {
                if (dateMapping.value.equalsIgnoreCase(input)) {
                    return Optional.of(dateMapping);
                }
            }
            return Optional.empty();
        }
    }

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
            return (ZonedDateTime.from(startTime).isBefore(start) && ZonedDateTime.from(endTime).isAfter(end)) ||
             (ZonedDateTime.from(startTime).isEqual(start) && ZonedDateTime.from(endTime).isEqual(end));
        }
        return false;
    }

    public static MaterializedRate fromRate(final Rate rate) {
        final String[] times = rate.getTimes().split("-");
        final LocalTime startTime = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HHMM"));
        final LocalTime endTime = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HHMM"));
        final Integer price = rate.getPrice();
        final List<DayOfWeek> daysApplied = Stream.of(rate.getDays().split(","))
                .map(DateMapping::valueOf)
                .map(o -> o.day)
                .collect(Collectors.toList());
        return new MaterializedRate(startTime, endTime, daysApplied, price);
    }
}
