package com.fake_company.spark_rest_example;

import com.beust.jcommander.ParameterException;
import com.fake_company.spark_rest_example.configuration.ApplicationConfiguration;
import com.fake_company.spark_rest_example.configuration.CommandLineArguments;
import com.fake_company.spark_rest_example.model.routes.CreateRateJsonRoute;
import com.fake_company.spark_rest_example.model.routes.CreateRateXmlRoute;
import com.fake_company.spark_rest_example.model.routes.EvaluateRateRoute;
import com.fake_company.spark_rest_example.model.transformers.JsonResponseTransformer;
import com.fake_company.spark_rest_example.model.transformers.XmlResponseTransformer;
import com.fake_company.spark_rest_example.repository.ParkingH2Repository;
import com.fake_company.spark_rest_example.repository.ParkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;

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
        } catch (IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
            System.exit(4);
        } catch (InstantiationException e) {
            LOG.error(e.getMessage(), e);
            System.exit(5);
        }
    }

    public void run(final CommandLineArguments commandLineArguments) throws IOException, IllegalAccessException, InstantiationException {
        final ApplicationConfiguration config = getConfiguration(commandLineArguments);
        final ParkingRepository parkingRepository = new ParkingH2Repository(config);
        // Build swagger json description
        port(commandLineArguments.getPort());
        path("/parking", () -> {
            get("/availability", new EvaluateRateRoute(parkingRepository));
            path("/import", () -> {
                post("/rates", "application/json", new CreateRateJsonRoute(parkingRepository), new JsonResponseTransformer());
                post("/rates", "application/xml", new CreateRateXmlRoute(parkingRepository), new XmlResponseTransformer());
            });
        });
        Runtime.getRuntime().addShutdownHook(new Thread(Spark::stop));
    }

}
