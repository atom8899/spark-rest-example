package com.fake_company.spark_rest_example.model.rate.validation;

import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class RateDayValidatorTests {

    @Test
    public void test_success_valid_days() {
        final var mockContext = mock(ConstraintValidatorContext.class);
        assertTrue(new RateDayValidator().isValid("mon,tues,wed", mockContext));
    }

    @Test
    public void test_success_valid_day() {
        final var mockContext = mock(ConstraintValidatorContext.class);
        assertTrue(new RateDayValidator().isValid("mon", mockContext));
    }

    @Test
    public void test_failure_bad_day_among_good_days() {
        final var mockContext = mock(ConstraintValidatorContext.class);
        assertFalse(new RateDayValidator().isValid("mon,tues,Rick", mockContext));
    }

    @Test
    public void test_failure_bad_day() {
        final var mockContext = mock(ConstraintValidatorContext.class);
        assertFalse(new RateDayValidator().isValid("Rick", mockContext));
    }
}
