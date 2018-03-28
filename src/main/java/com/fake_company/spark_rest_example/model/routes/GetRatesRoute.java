package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.ParkingRepository;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetRatesRoute implements Route {

    private final ParkingRepository parkingRepository;

    public GetRatesRoute(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        return new ApiResponse(ApiResponse.ResponseStatus.Success, "Current Rates", new Rates(parkingRepository.getRates()));
    }
}
