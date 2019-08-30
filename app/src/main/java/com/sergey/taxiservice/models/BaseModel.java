package com.sergey.taxiservice.models;

import com.google.gson.annotations.SerializedName;

public class BaseModel {

    @SerializedName("success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
