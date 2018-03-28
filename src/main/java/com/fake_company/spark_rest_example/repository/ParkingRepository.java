package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.model.rate.Rate;

import java.util.List;

public interface ParkingRepository {
    public Rate persistRate(final Rate rate);

    public List<Rate> getRates();

    public List<MaterializedRate> getMaterializedRates();
}
