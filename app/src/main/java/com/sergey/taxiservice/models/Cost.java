package com.sergey.taxiservice.models;

import com.google.gson.annotations.SerializedName;

public class Cost extends BaseModel {

    @SerializedName("cost")
    private String cost;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
