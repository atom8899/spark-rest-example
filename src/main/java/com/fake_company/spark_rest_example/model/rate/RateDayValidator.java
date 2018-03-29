package com.fake_company.spark_rest_example.model.rate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.util.stream.Stream;

public class RateDayValidator implements ConstraintValidator{
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        } else {
            final String dayList = value.toString();
            if(dayList.contains(",")) {
                Stream.of(dayList.split(",")).allMatch(WeekDayMapper::parse));
            }
        }
        return false;
    }
}
