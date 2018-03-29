package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.RateRepository;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Returns all existing rates in the system
 */
public class GetRatesRoute implements Route {

    private final RateRepository rateRepository;

    public GetRatesRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        return new ApiResponse(ApiResponse.ResponseStatus.Success, "Current Rates", new Rates(rateRepository.getRates()));
    }
}
