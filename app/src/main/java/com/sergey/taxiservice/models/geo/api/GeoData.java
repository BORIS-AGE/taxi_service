package com.sergey.taxiservice.models.geo.api;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;

public class GeoData extends BaseModel {

    @SerializedName("response")
    private GeoResponse response;

    public GeoResponse getResponse() {
        return response;
    }

    public void setResponse(GeoResponse response) {
        this.response = response;
    }
}