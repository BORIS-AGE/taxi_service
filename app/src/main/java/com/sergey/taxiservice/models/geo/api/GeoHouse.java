package com.sergey.taxiservice.models.geo.api;

import com.google.gson.annotations.SerializedName;

public class GeoHouse {

    @SerializedName("house")
    private String house;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}