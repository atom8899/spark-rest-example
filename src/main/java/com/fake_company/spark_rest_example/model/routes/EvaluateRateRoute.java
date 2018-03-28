package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.repository.RateRepository;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EvaluateRateRoute implements Route {

    private final RateRepository rateRepository;

    public EvaluateRateRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
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

        final List<MaterializedRate> rates = rateRepository.getMaterializedRates().stream().filter(r -> r.isWithinRate(startTime, endTime)).collect(Collectors.toList());
        response.status(200);
        if(rates.isEmpty()) {
            return new ApiResponse(ApiResponse.ResponseStatus.Success, "Non Available");
        } else {
            return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rate Found", rates.get(0).getPrice());
        }
    }
}
