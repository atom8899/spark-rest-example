package com.fake_company.spark_rest_example.model;

import com.google.gson.JsonElement;

public class ApiResponse {
    private ResponseStatus status;
    private String message;
    private Object data;

    public ApiResponse() {}

    public ApiResponse(final ResponseStatus responseStatus) {
        this.status = responseStatus;
    }

    public ApiResponse(final ResponseStatus responseStatus, final String message) {
        this(responseStatus);
        this.message = message;
    }

    public ApiResponse(final ResponseStatus responseStatus, final String message, final Object jsonElement) {
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

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum ResponseStatus {
        Success,
        Failure
    }
}
