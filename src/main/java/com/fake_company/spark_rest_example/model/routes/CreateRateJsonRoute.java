package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.Rate;
import com.fake_company.spark_rest_example.model.Rates;
import com.fake_company.spark_rest_example.repository.ParkingRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CreateRateJsonRoute implements Route {

    private final ParkingRepository parkingRepository;

    public CreateRateJsonRoute(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        final Type listType = new TypeToken<ArrayList<Rate>>(){}.getType();
        final Rates rate = new Gson().fromJson(request.body(), Rates.class);
        rate.forEach(parkingRepository::persistRate);
        response.status(200);
        return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rates Created");
    }
}
