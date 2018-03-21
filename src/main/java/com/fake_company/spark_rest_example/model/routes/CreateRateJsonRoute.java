package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.repository.ParkingRepository;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateRateJsonRoute implements Route {

    private final ParkingRepository parkingRepository;

    public CreateRateJsonRoute(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
