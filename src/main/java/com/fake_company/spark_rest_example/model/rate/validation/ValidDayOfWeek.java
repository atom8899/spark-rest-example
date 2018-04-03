package com.fake_company.spark_rest_example.model.rate.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RateDayValidator.class)
@Documented
public @interface ValidDayOfWeek {
    String message() default "{com.fake_company.validator.day_validator." +
            "message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    DayValidation value();

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidDayOfWeek[] value();
    }
}
