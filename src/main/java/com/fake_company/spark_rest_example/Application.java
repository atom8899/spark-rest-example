package com.fake_company.spark_rest_example;

import com.fake_company.spark_rest_example.configuration.CommandLineArguments;
import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.routes.CreateRateRoute;
import com.fake_company.spark_rest_example.model.routes.EvaluateRateRoute;
import com.fake_company.spark_rest_example.model.routes.GetRatesRoute;
import com.fake_company.spark_rest_example.model.transformers.JsonResponseTransformer;
import com.fake_company.spark_rest_example.model.transformers.XmlResponseTransformer;
import com.fake_company.spark_rest_example.repository.RateH2Repository;
import com.fake_company.spark_rest_example.repository.RateRepository;
import com.google.gson.Gson;
import com.google.gson.stream.MalformedJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

public class Application {
    final private static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        try {
            final var main = new Application();
            final var commandLineArguments = CommandLineArguments.parse(args);
            main.run(commandLineArguments);
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);
            System.exit(1);
        }
    }

    /**
     * Constructs the paths and establishes Routes
     * @param commandLineArguments
     */
    public void run(final CommandLineArguments commandLineArguments) {
        final RateRepository rateRepository = RateH2Repository.getInstance();
        port(commandLineArguments.getPort());
        path("/parking", () -> {
            get("/availability", "application/json", new EvaluateRateRoute(rateRepository), new JsonResponseTransformer());
            get("/availability", "application/xml", new EvaluateRateRoute(rateRepository), new XmlResponseTransformer());
            get("/rates", "application/json", new GetRatesRoute(rateRepository), new JsonResponseTransformer());
            get("/rates", "application/xml", new GetRatesRoute(rateRepository), new XmlResponseTransformer());
            path("/import", () -> {
                post("/rates", "application/json", new CreateRateRoute(rateRepository), new JsonResponseTransformer());
                post("/rates", "application/xml", new CreateRateRoute(rateRepository), new XmlResponseTransformer());
            });
        });
        notFound((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new ApiResponse(ApiResponse.ResponseStatus.Failure, "Route not found"));

        });
        exception(MalformedJsonException.class, (exception, request, response) -> {
            response.type("application/json");
            response.body(new Gson().toJson(new ApiResponse(ApiResponse.ResponseStatus.Failure, "Malformed JSON", exception.getMessage())));
        });
        internalServerError((req, res) -> {
            res.type("application/json");
            return new Gson().toJson(new ApiResponse(ApiResponse.ResponseStatus.Failure, "Internal System Error Occurred. Please try again later ... "));
        });
        Runtime.getRuntime().addShutdownHook(new Thread(Spark::stop));
    }

    public void shutdown() {
        Spark.stop();
    }

}
