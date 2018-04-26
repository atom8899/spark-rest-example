package com.fake_company.spark_rest_example;

import com.fake_company.spark_rest_example.configuration.CommandLineArguments;
import com.fake_company.spark_rest_example.model.ApiResponse;
import com.google.gson.Gson;
import org.apache.commons.codec.StringEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SubmitRatesIntegrationTest {

    private Gson gson = new Gson();

    @Test
    public void SubmitRatesTest_Success() throws Exception {
        Application application = new Application();
        application.run(new CommandLineArguments());
        final String testRatesJson = "{\n" +
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
        HttpPost postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final StringEntity jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_JSON);
        postRatesRequest.setEntity(jsonEntity);

        HttpResponse response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final ApiResponse apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getMessage(), "Rates Created");
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Success);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);


    }

    @Test
    public void SubmitRatesTest_Failure_MissingRequiredFields() throws Exception {
        Application application = new Application();
        application.run(new CommandLineArguments());
        final String testRatesJson = "{\n" +
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
        HttpPost postRatesRequest = new HttpPost("http://localhost:9000/parking/import/rates");
        final StringEntity jsonEntity = new StringEntity(testRatesJson, ContentType.APPLICATION_JSON);
        postRatesRequest.setEntity(jsonEntity);

        HttpResponse response = httpClient.execute(postRatesRequest);
        response.getEntity().getContent();
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        final ApiResponse apiResponse = gson.fromJson(rd, ApiResponse.class);
        Assert.assertEquals(apiResponse.getMessage(), "Rates Created");
        Assert.assertEquals(apiResponse.getStatus(), ApiResponse.ResponseStatus.Success);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);


    }
}
