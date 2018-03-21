package com.fake_company.spark_rest_example.repository;

import com.fake_company.spark_rest_example.model.Rates;

public interface ParkingRepository {
    public Rates persistRate(final Rates rate);
}
