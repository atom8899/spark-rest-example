package com.fake_company.spark_rest_example.model.rate.validation;

import com.fake_company.spark_rest_example.model.rate.Rate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Determines if a string contains a single/and or list of valid week day values
 */
public class RateDayValidator implements ConstraintValidator<ValidDayOfWeek,String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return false;
        } else {
            if(value.contains(",")) {
                return Stream.of(value.split(",")).map(WeekDayMapper::parse).allMatch(Optional::isPresent);
            } else {
                return WeekDayMapper.parse(value).isPresent();
            }
        }
    }
}
