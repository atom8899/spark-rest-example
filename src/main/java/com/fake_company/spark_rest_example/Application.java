package com.fake_company.spark_rest_example;

import com.beust.jcommander.ParameterException;
import com.fake_company.spark_rest_example.configuration.ApplicationConfiguration;
import com.fake_company.spark_rest_example.configuration.CommandLineArguments;
import com.fake_company.spark_rest_example.model.routes.*;
import com.fake_company.spark_rest_example.model.transformers.JsonResponseTransformer;
import com.fake_company.spark_rest_example.model.transformers.XmlResponseTransformer;
import com.fake_company.spark_rest_example.repository.RateH2Repository;
import com.fake_company.spark_rest_example.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;
import java.sql.SQLException;

import static com.fake_company.spark_rest_example.configuration.ApplicationConfiguration.getConfiguration;
import static spark.Spark.*;

public class Application {
    final private static Logger LOG = LoggerFactory.getLogger(Application.class);
    final private static String APP_PACKAGE = "com.fake_company.spark_rest_example";

    public static void main(final String[] args) {
        try {
            final Application main = new Application();
            final CommandLineArguments commandLineArguments = CommandLineArguments.parse(args);
            main.run(commandLineArguments);
        } catch (ParameterException pe) {
            LOG.error(pe.getMessage());
            System.exit(2);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            System.exit(3);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(4);
        }
    }

    public void run(final CommandLineArguments commandLineArguments) throws IOException, SQLException {
        final ApplicationConfiguration config = getConfiguration(commandLineArguments);
        final RateRepository rateRepository = new RateH2Repository(config);
        // Build swagger json description
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
        Runtime.getRuntime().addShutdownHook(new Thread(Spark::stop));
    }

}
