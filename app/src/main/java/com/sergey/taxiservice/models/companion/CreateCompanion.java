package com.sergey.taxiservice.models.companion;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;

public class CreateCompanion extends BaseModel {

    @SerializedName("ride_companion_request")
    private Companion companion;

    public Companion getCompanion() {
        return companion;
    }

    public void setCompanion(Companion companion) {
        this.companion = companion;
    }
}
