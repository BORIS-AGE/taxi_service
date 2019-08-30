package com.sergey.taxiservice.models.order;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;
import com.sergey.taxiservice.models.ride.Ride;

public class CreateOrder extends BaseModel {

    @SerializedName("ride")
    private Ride ride;

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
