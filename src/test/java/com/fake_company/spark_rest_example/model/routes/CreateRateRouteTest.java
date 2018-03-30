package com.fake_company.spark_rest_example.model.routes;

import com.fake_company.spark_rest_example.model.ApiResponse;
import com.fake_company.spark_rest_example.model.rate.Rate;
import com.fake_company.spark_rest_example.model.rate.Rates;
import com.fake_company.spark_rest_example.repository.RateRepository;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CreateRateRouteTest {

    @Test
    public void CreateRateJsonRouteTest_failure_missing_price() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2100\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(400);
        verify(mockRepository, never()).persistRate(any());
        final Rate firstRate = ((Rates) apiResponse.getData()).getRates().get(0);
        assertTrue(firstRate.getDays().equals("mon,tues,thurs"));
        assertTrue(firstRate.getTimes().equals("0900-2100"));
        assertTrue(firstRate.getPrice() == null);
        assertFalse(firstRate.getValidations().isEmpty());
        assertTrue(firstRate.getValidations().get(0).contains("price"));
    }

    @Test
    public void CreateRateJsonRouteTest_failure_missing_days() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"times\":\"0900-2100\", \"price\":\"9000\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(400);
        verify(mockRepository, never()).persistRate(any());
        final Rate firstRate = ((Rates) apiResponse.getData()).getRates().get(0);
        assertTrue(firstRate.getTimes().equals("0900-2100"));
        assertTrue(firstRate.getPrice().equals(9000));
        assertTrue(firstRate.getDays() == null);
        assertTrue(firstRate.getValidations().get(0).contains("days"));
    }

    @Test
    public void CreateRateJsonRouteTest_failure_missing_times() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"days\":\"mon,tues,thurs\", \"price\":\"9000\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(400);
        verify(mockRepository, never()).persistRate(any());
        final Rate firstRate = ((Rates) apiResponse.getData()).getRates().get(0);
        assertTrue(firstRate.getDays().equals("mon,tues,thurs"));
        assertTrue(firstRate.getPrice().equals(9000));
        assertTrue(firstRate.getTimes() == null);
        assertTrue(firstRate.getValidations().get(0).contains("times"));
    }

    @Test
    public void CreateRateJsonRouteTest_failure_missing_invalid_days() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"times\":\"9000-1000\",\"days\":\"mon,january,thurs\", \"price\":\"9000\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(400);
        verify(mockRepository, never()).persistRate(any());
        final Rate firstRate = ((Rates) apiResponse.getData()).getRates().get(0);
        assertTrue(firstRate.getDays().equals("mon,january,thurs"));
        assertTrue(firstRate.getPrice().equals(9000));
        assertTrue(firstRate.getTimes().equals("9000-1000"));
        assertTrue(firstRate.getValidations().get(0).contains("Must be a valid day of the week. Acceptable values: mon,tues,wed,thurs,fri,sat,sun"));
    }

    @Test
    public void CreateRateJsonRouteTest_failure_missing_invalid_times() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"times\":\"90001000\",\"days\":\"mon,tues,thurs\", \"price\":\"9000\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(400);
        verify(mockRepository, never()).persistRate(any());
        final Rate firstRate = ((Rates) apiResponse.getData()).getRates().get(0);
        assertTrue(firstRate.getDays().equals("mon,tues,thurs"));
        assertTrue(firstRate.getPrice().equals(9000));
        assertTrue(firstRate.getTimes().equals("90001000"));
        assertTrue(firstRate.getValidations().get(0).contains("must provide two times in the format HHMM-HHMM"));
    }

    @Test
    public void CreateRateJsonRouteTest_success() throws Exception {
        final RateRepository mockRepository = mock(RateRepository.class);
        final CreateRateRoute createRoute = new CreateRateRoute(mockRepository);
        final Request mockRequest = mock(Request.class);
        final Response mockResponse = mock(Response.class);
        when(mockRequest.headers(HttpHeader.CONTENT_TYPE.asString())).thenReturn("application/json");
        when(mockRequest.body()).thenReturn("{\"rates\":[{\"times\":\"9000-1000\",\"days\":\"mon,tues,thurs\", \"price\":\"9000\"}]}");
        final ApiResponse apiResponse = (ApiResponse) createRoute.handle(mockRequest, mockResponse);
        verify(mockResponse, times(1)).status(200);
        ArgumentCaptor<Rate> rateCaptor = ArgumentCaptor.forClass(Rate.class);
        verify(mockRepository, times(1)).persistRate(rateCaptor.capture());
        final Rate firstRate = rateCaptor.getAllValues().get(0);
        assertTrue(firstRate.getDays().equals("mon,tues,thurs"));
        assertTrue(firstRate.getPrice().equals(9000));
        assertTrue(firstRate.getTimes().equals("9000-1000"));
        assertTrue(firstRate.getValidations().isEmpty());
    }
}
