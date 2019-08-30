package com.sergey.taxiservice.models.companion;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;

public class RideInfo extends BaseModel {

    @SerializedName("ride")
    private RideGeneralInfo rideGeneralInfo;

    public RideGeneralInfo getRideGeneralInfo() {
        return rideGeneralInfo;
    }

    public void setRideGeneralInfo(RideGeneralInfo rideGeneralInfo) {
        this.rideGeneralInfo = rideGeneralInfo;
    }
}
