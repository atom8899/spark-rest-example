package com.fake_company.spark_rest_example.model.exception;

import com.fake_company.spark_rest_example.model.rate.Rate;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class InvalidRateException extends Exception {
    private Rate rate;
    private String message;
    private Set<ConstraintViolation<Rate>> constraintViolations;

    public InvalidRateException(final String message, final Rate rate, final Set<ConstraintViolation<Rate>> constraintViolations) {
        super(message);
        this.message = message;
        this.rate = rate;
        this.constraintViolations = constraintViolations;
    }
}
