package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.repository.RateRepository;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Evaluates the provided time range and attempts to find a valid rate.
 * Rate is returned if found.
 * None Available is returned if no rates match the timeframe
 */
public class EvaluateRateRoute implements Route {

    private final RateRepository rateRepository;

    public EvaluateRateRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        try {
            final QueryParamsMap queryStartParamsMap = request.queryMap("start_time");
            final QueryParamsMap queryEndParamsMap = request.queryMap("end_time");
            if (queryStartParamsMap.hasValue() && queryEndParamsMap.hasValue()) {
                final ZonedDateTime startTime = ZonedDateTime.parse(queryStartParamsMap.value());
                final ZonedDateTime endTime = ZonedDateTime.parse(queryEndParamsMap.value());

                if (startTime.isAfter(endTime)) {
                    throw new Exception("Please provide a valid Start and End time");
                }

                final List<MaterializedRate> rates = rateRepository.getMaterializedRates().stream().filter(r -> r.isWithinRate(startTime, endTime)).collect(Collectors.toList());
                response.status(200);
                if (rates.isEmpty()) {
                    return new ApiResponse(ApiResponse.ResponseStatus.Success, "None Available");
                } else {
                    return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rate Found", rates.get(0).getPrice());
                }
            } else {
                response.status(400);
                return new ApiResponse(ApiResponse.ResponseStatus.Failure, "Invalid Request. Must include start_time and end_time url params for Rate Availability API");

            }
        } catch (DateTimeParseException e) {
            response.status(400);
            return new ApiResponse(ApiResponse.ResponseStatus.Failure, "Invalid Request. Date times provided are invalid. Format yyyy-MM-ddTHH:mm:ssZ" );
        }
    }
}
