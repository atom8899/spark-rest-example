package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.RateRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.Headers;
import org.eclipse.jetty.http.HttpHeader;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CreateRateRoute implements Route {

    private final RateRepository rateRepository;

    public CreateRateRoute(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        if("application/xml".equalsIgnoreCase(request.headers(HttpHeader.CONTENT_TYPE.asString()))) {
            final XmlMapper xmlMapper = new XmlMapper();
            final Rates rates = xmlMapper.readValue(request.body(), Rates.class);
            rates.forEach(rateRepository::persistRate);
            response.status(200);
            return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rates Created");
        } else {
            final Rates rate = new Gson().fromJson(request.body(), Rates.class);
            rate.forEach(rateRepository::persistRate);
            response.status(200);
            return new ApiResponse(ApiResponse.ResponseStatus.Success, "Rates Created");
        }
    }
}
