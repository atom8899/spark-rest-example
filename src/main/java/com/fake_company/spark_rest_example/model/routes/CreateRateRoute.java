package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.RateRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpHeader;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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

    public CreateRateRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        if("application/xml".equalsIgnoreCase(request.headers(HttpHeader.CONTENT_TYPE.asString()))) {
            final XmlMapper xmlMapper = new XmlMapper();
            final Rates rates = xmlMapper.readValue(request.body(), Rates.class);
            return processRates(rates, response);
        } else {
            final Rates rates = new Gson().fromJson(request.body(), Rates.class);
            return processRates(rates, response);
        }
    }

    public ApiResponse processRates(final Rates rates, final Response response) {
        final List<Rate> validatedRates = rates.getRates()
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
