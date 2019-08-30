package com.sergey.taxiservice.api.legacy;

import android.support.annotation.Nullable;

import org.json.JSONObject;

public class ApiResponse<T> {

    @Nullable
    private JSONObject errors;
    private T response;
    private boolean success;

    ApiResponse(@Nullable JSONObject errors, boolean success, T response) {
        this.errors = errors;
        this.response = response;
        this.success = success;
    }

    public boolean hasErrors(){
        return errors != null;
    }

    @Nullable
    public JSONObject getErrors() {
        return errors;
    }

    public boolean isSuccess(){
        return success;
    }

    public T getResponse() {
        return response;
    }
}
