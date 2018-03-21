package com.fake_company.spark_rest_example.model;

import com.google.gson.JsonElement;

public class ApiResponse {
    private ResponseStatus status;
    private String message;
    private JsonElement data;

    public ApiResponse(final ResponseStatus responseStatus) {
        this.status = responseStatus;
    }

    public ApiResponse(final ResponseStatus responseStatus, final String message) {
        this(responseStatus);
        this.message = message;
    }

    public ApiResponse(final ResponseStatus responseStatus, final String message, JsonElement jsonElement) {
        this(responseStatus);
        this.message = message;
        this.data = jsonElement;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    enum ResponseStatus {
        Success,
        Failure
    }
}
