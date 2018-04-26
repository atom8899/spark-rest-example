package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.MaterializedRate;
import com.fake_company.spark_rest_example.repository.RateRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class EvaluateRateRouteTest {

    @Test
    public void evaluateRate_success_rate_found() throws Exception {
        final var mockRepo = mock(RateRepository.class);
        final var mockRequest = mock(Request.class);
        final var mockResponse = mock(Response.class);

        final var startQueryParamsMap = mock(QueryParamsMap.class);
        final var endQueryParamsMap = mock(QueryParamsMap.class);
        when(mockRequest.queryMap("start_time")).thenReturn(startQueryParamsMap);
        when(mockRequest.queryMap("end_time")).thenReturn(endQueryParamsMap);
        when(startQueryParamsMap.hasValue()).thenReturn(true);
        when(endQueryParamsMap.hasValue()).thenReturn(true);
        when(startQueryParamsMap.value()).thenReturn("2015-07-01T07:00:00Z");
        when(endQueryParamsMap.value()).thenReturn("2015-07-01T12:00:00Z");
        final var mockMaterializedRate = mock(MaterializedRate.class);
        when(mockMaterializedRate.getPrice()).thenReturn(1000);
        when(mockRepo.getMaterializedRates()).thenReturn(Lists.newArrayList(mockMaterializedRate));
        when(mockMaterializedRate.isWithinRate(any(ZonedDateTime.class), any(ZonedDateTime.class))).thenReturn(true);
        final var apiResponse = (ApiResponse) new EvaluateRateRoute(mockRepo).handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(200);
        assertTrue(apiResponse.getData().equals(1000));

    }

    @Test
    public void evaluateRate_success_no_rate_found() throws Exception {
        final var mockRepo = mock(RateRepository.class);
        final var mockRequest = mock(Request.class);
        final var mockResponse = mock(Response.class);

        final var startQueryParamsMap = mock(QueryParamsMap.class);
        final var endQueryParamsMap = mock(QueryParamsMap.class);
        when(mockRequest.queryMap("start_time")).thenReturn(startQueryParamsMap);
        when(mockRequest.queryMap("end_time")).thenReturn(endQueryParamsMap);
        when(startQueryParamsMap.hasValue()).thenReturn(true);
        when(endQueryParamsMap.hasValue()).thenReturn(true);
        when(startQueryParamsMap.value()).thenReturn("2015-07-01T07:00:00Z");
        when(endQueryParamsMap.value()).thenReturn("2015-07-01T12:00:00Z");
        final var mockMaterializedRate = mock(MaterializedRate.class);
        when(mockRepo.getMaterializedRates()).thenReturn(Lists.newArrayList(mockMaterializedRate));
        when(mockMaterializedRate.isWithinRate(any(ZonedDateTime.class), any(ZonedDateTime.class))).thenReturn(false);
        final var apiResponse = (ApiResponse) new EvaluateRateRoute(mockRepo).handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(200);
        assertTrue(apiResponse.getMessage().equals("None Available"));
    }
}