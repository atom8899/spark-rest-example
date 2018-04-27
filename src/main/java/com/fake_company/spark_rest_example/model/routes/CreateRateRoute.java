package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.XMLObjectMapper;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.RateRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates rates in the system.
 * Rates are validated against JPA constraints.
 * If the provided Rates are valid, existing rates in the system are purged and the new rates are imported
 */
public class CreateRateRoute implements Route {

    private final RateRepository rateRepository;
    private final Validator validator;
    private final XMLObjectMapper xmlMapper = new XMLObjectMapper();

    public CreateRateRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
        var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public Object handle(final Request request, final Response response) {
        try {
            if(request.contentType().contains("application/xml")) {
                final var rates = xmlMapper.readValue(request.body(), Rates.class);
                return processRates(rates, response);
            } else {
                final var rates = new Gson().fromJson(request.body(), Rates.class);
                return processRates(rates, response);
            }
        } catch (JsonSyntaxException | JsonParseException | JsonMappingException | MalformedJsonException e) {//GSON Exception Mapping is bad. Some extend _runtime_ some are just plain IO issues? wtf
            return new ApiResponse(ApiResponse.ResponseStatus.Failure, "Malformed Rates Request", e.getMessage());
        } catch (IOException e) {
            return new ApiResponse(ApiResponse.ResponseStatus.Failure, "Malformed Rates Request", e.getMessage());
        }
    }

    public ApiResponse processRates(final Rates rates, final Response response) {
        final var validatedRates = rates.getRates()
                .stream()
                .peek(r -> r.setValidations(validator.validate(r)
                        .stream()
                        .map(v -> String.format("%s:%s", v.getPropertyPath().toString(), v.getMessage()))
                        .collect(Collectors.toList()))
                ).collect(Collectors.toList());
        if(validatedRates.stream().allMatch(r -> r.getValidations().isEmpty())) {
            rateRepository.clearExistingRates();
            rates.getRates().forEach(rateRepository::persistRate);
            response.status(200);
            return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rates Created");
        } else {
            response.status(400);
            return new ApiResponse(ApiResponse.ResponseStatus.Failure, "Invalid Rate Submission", rates);
        }
    }
}
