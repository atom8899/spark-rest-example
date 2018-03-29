package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.model.exception.InvalidRateException;
import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.model.rate.Rate;

import java.util.List;

/**
 * A repository contract for persisting and retrieving rates
 */
public interface RateRepository {
    /**
     * Persist rate into the repository
     * @param rate
     * @return
     */
    public Rate persistRate(final Rate rate);

    /**
     * Retrieve all current rates from the repository
     * @return
     */
    public List<Rate> getRates();

    /**
     * Retrieve all current rates from the repository and materialize them
     * @return
     */
    public List<MaterializedRate> getMaterializedRates();

    /**
     * Remove existing rates from the repository
     */
    public void clearExistingRates();
}
