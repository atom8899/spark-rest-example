package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.repository.RateRepository;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RouteTests {

    @Test
    public void CreateRateJsonRouteTest_Success() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2100\",\"price\":1500},{\"days\":\"fri,sat,sun\",\"times\":\"0900-2100\",\"price\":2000},{\"days\":\"wed\",\"times\":\"0600-1800\",\"price\":1750},{\"days\":\"mon,wed,sat\",\"times\":\"0100-0500\",\"price\":1000},{\"days\":\"sun,tues\",\"times\":\"0100-0700\",\"price\":925}]}");
        ArgumentCaptor<Rate> peopleCaptor = ArgumentCaptor.forClass(Rate.class);
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockRepository, times(5)).persistRate(peopleCaptor.capture());
        verify(mockResponse, times(1)).status(200);
    }
}
