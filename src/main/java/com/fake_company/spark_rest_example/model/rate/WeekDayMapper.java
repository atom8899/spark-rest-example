package com.fake_company.spark_rest_example.model.rate;

import java.time.DayOfWeek;
import java.util.Optional;

public enum WeekDayMapper {
    mon("mon", DayOfWeek.MONDAY),
    tues("tues", DayOfWeek.TUESDAY),
    wed("wed", DayOfWeek.WEDNESDAY),
    thurs("thurs", DayOfWeek.THURSDAY),
    fri("fri", DayOfWeek.FRIDAY),
    sat("sat", DayOfWeek.SATURDAY),
    sun("sun", DayOfWeek.SUNDAY);

    public String value;
    public DayOfWeek day;

    WeekDayMapper(final String value, final DayOfWeek day) {
        this.value = value;
        this.day = day;
    }

    public String getValue() {
        return value;
    }

    public static Optional<WeekDayMapper> parse(final String input) {
        for (WeekDayMapper dateMapping : WeekDayMapper.values()) {
            if (dateMapping.value.equalsIgnoreCase(input)) {
                return Optional.of(dateMapping);
            }
        }
        return Optional.empty();
    }
}
