package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.Rate;
import com.fake_company.spark_rest_example.model.Rates;
import com.fake_company.spark_rest_example.repository.ParkingRepository;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class EvaluateRateRoute implements Route {

    private final ParkingRepository parkingRepository;

    public EvaluateRateRoute(final ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        final QueryParamsMap queryStartParamsMap = request.queryMap("start_time");
        final QueryParamsMap queryEndParamsMap = request.queryMap("end_time");
        final ZonedDateTime startTime = ZonedDateTime.parse(queryStartParamsMap.value());
        final ZonedDateTime endTime = ZonedDateTime.parse(queryEndParamsMap.value());
        if(startTime.isAfter(endTime)) {
            throw new Exception("Please provide a valid Start and End time");
        }
        List<ZonedDateTime>

        final List<Rate> currentRates = parkingRepository.getRates();
        return null;
    }
}
