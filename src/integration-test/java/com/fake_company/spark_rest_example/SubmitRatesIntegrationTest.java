package com.fake_company.spark_rest_example;

import com.fake_company.spark_rest_example.configuration.CommandLineArguments;
import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.repository.RateH2Repository;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class SubmitRatesIntegrationTest {

    private Gson gson = new Gson();
    private static Application application = new Application();
    private static RateH2Repository rateH2Repository;

    @BeforeClass//Lingering VM objects require the BeforeClass/After to properly cleanup
    public static void setup() throws IOException, SQLException {
        application.run(new CommandLineArguments());
        rateH2Repository = RateH2Repository.getInstance();
    }

    @AfterClass
    public static void destroy() {
        application.shutdown();
    }

    @Test
    public void SubmitRatesTest_Success_JSON() throws Exception {
        final var testRatesJson = "{\n" +
                "    \"rates\": [\n" +
                "        {\n" +
                "            \"days\": \"mon,tues,thurs\",\n" +
                "            \"times\": \"0900-2100\",\n" +
                "            \"price\": 1500\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"fri,sat,sun\",\n" +
                "            \"times\": \"0900-2100\",\n" +
                "            \"price\": 2000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"wed\",\n" +
                "            \"times\": \"0600-1800\",\n" +
                "            \"price\": 1750\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"mon,wed,sat\",\n" +
                "            \"times\": \"0100-0500\",\n" +
                "            \"price\": 1000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"sun,tues\",\n" +
                "            \"times\": \"0100-0700\",\n" +
                "            \"price\": 925\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        final HttpClient httpClient = HttpClientBuilder.create().build();
        var postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final var jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_JSON);
        postRatesRequest.setEntity(jsonEntity);

        var response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        var rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final var apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getMessage(), "Rates Created");
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Success);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(rateH2Repository.getRates().size(),5);

        final var rate1 = rateH2Repository.getRates().get(0);
        final var rate2 = rateH2Repository.getRates().get(1);
        final var rate3 = rateH2Repository.getRates().get(2);
        final var rate4 = rateH2Repository.getRates().get(3);
        final var rate5 = rateH2Repository.getRates().get(4);

        Assert.assertEquals(rate1.getDays(), "mon,tues,thurs");
        Assert.assertEquals(rate1.getTimes(), "0900-2100");
        Assert.assertTrue(rate1.getPrice() == 1500);

        Assert.assertEquals(rate2.getDays(), "fri,sat,sun");
        Assert.assertEquals(rate2.getTimes(), "0900-2100");
        Assert.assertTrue(rate2.getPrice() == 2000);

        Assert.assertEquals(rate3.getDays(), "wed");
        Assert.assertEquals(rate3.getTimes(), "0600-1800");
        Assert.assertTrue(rate3.getPrice() ==1750);

        Assert.assertEquals(rate4.getDays(), "mon,wed,sat");
        Assert.assertEquals(rate4.getTimes(), "0100-0500");
        Assert.assertTrue(rate4.getPrice() == 1000);

        Assert.assertEquals(rate5.getDays(), "sun,tues");
        Assert.assertEquals(rate5.getTimes(), "0100-0700");
        Assert.assertTrue(rate5.getPrice() == 925);


    }

    @Test
    public void SubmitRatesTest_Success_XML() throws Exception {
        final var testRatesJson = "<Rates><rates><rate><id/><times>0900-2100</times><price>1500</price><days>mon,tues,thurs</days></rate><rate><id/><times>0900-2100</times><price>2000</price><days>fri,sat,sun</days></rate><rate><id/><times>0600-1800</times><price>1750</price><days>wed</days></rate><rate><id/><times>0100-0500</times><price>1000</price><days>mon,wed,sat</days></rate><rate><id/><times>0100-0700</times><price>925</price><days>sun,tues</days></rate></rates></Rates>";

        final HttpClient httpClient = HttpClientBuilder.create().build();
        var postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final var jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_XML);
        postRatesRequest.setEntity(jsonEntity);

        var response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        var rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final var apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getMessage(), "Rates Created");
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Success);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Assert.assertEquals(rateH2Repository.getRates().size(),5);

        final var rate1 = rateH2Repository.getRates().get(0);
        final var rate2 = rateH2Repository.getRates().get(1);
        final var rate3 = rateH2Repository.getRates().get(2);
        final var rate4 = rateH2Repository.getRates().get(3);
        final var rate5 = rateH2Repository.getRates().get(4);

        Assert.assertEquals(rate1.getDays(), "mon,tues,thurs");
        Assert.assertEquals(rate1.getTimes(), "0900-2100");
        Assert.assertTrue(rate1.getPrice() == 1500);

        Assert.assertEquals(rate2.getDays(), "fri,sat,sun");
        Assert.assertEquals(rate2.getTimes(), "0900-2100");
        Assert.assertTrue(rate2.getPrice() == 2000);

        Assert.assertEquals(rate3.getDays(), "wed");
        Assert.assertEquals(rate3.getTimes(), "0600-1800");
        Assert.assertTrue(rate3.getPrice() ==1750);

        Assert.assertEquals(rate4.getDays(), "mon,wed,sat");
        Assert.assertEquals(rate4.getTimes(), "0100-0500");
        Assert.assertTrue(rate4.getPrice() == 1000);

        Assert.assertEquals(rate5.getDays(), "sun,tues");
        Assert.assertEquals(rate5.getTimes(), "0100-0700");
        Assert.assertTrue(rate5.getPrice() == 925);




    }

    @Test
    public void SubmitRatesTest_Failure_MalformedJSON() throws Exception {
        final var testRatesJson = "{\n" +
                "    \"rates\": [\n" +
                "        {\n" +
                "            \"days\": \"mon,tues,thurs\",\n" +
                "            \"price\": 1500\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"fri,sat,sun\",\n" +
                "            \"price\": 2000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"wed\",\n" +
                "            \"times\": \"0600-1800\",\n" +
                "        },\n" +
                "        {\n" +
                "            \"times\": \"0100-0500\",\n" +
                "            \"price\": 1000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"sun,tues\",\n" +
                "            \"times\": \"0100-0700\",\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        final HttpClient httpClient = HttpClientBuilder.create().build();
        var postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final var jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_JSON);
        postRatesRequest.setEntity(jsonEntity);

        var response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        var rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final var apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Failure);
        Assert.assertEquals(apiResponse.getMessage(), "Malformed Rates Request");
        Assert.assertEquals(rateH2Repository.getRates().size(),0);

    }

    @Test
    public void SubmitRatesTest_Failure_MalformedJSON_itsActuallyXML() throws Exception {
        final var testRatesJson = "{\n" +
                "    \"rates\": [\n" +
                "        {\n" +
                "            \"days\": \"mon,tues,thurs\",\n" +
                "            \"price\": 1500\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"fri,sat,sun\",\n" +
                "            \"price\": 2000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"wed\",\n" +
                "            \"times\": \"0600-1800\",\n" +
                "        },\n" +
                "        {\n" +
                "            \"times\": \"0100-0500\",\n" +
                "            \"price\": 1000\n" +
                "        },\n" +
                "        {\n" +
                "            \"days\": \"sun,tues\",\n" +
                "            \"times\": \"0100-0700\",\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        final HttpClient httpClient = HttpClientBuilder.create().build();
        var postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final var jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_XML);
        postRatesRequest.setEntity(jsonEntity);

        var response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        var rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final var apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Failure);
        Assert.assertEquals(apiResponse.getMessage(), "Malformed Rates Request");
        Assert.assertEquals(rateH2Repository.getRates().size(),0);

    }
}
