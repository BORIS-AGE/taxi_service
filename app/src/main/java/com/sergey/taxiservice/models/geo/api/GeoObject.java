
package com.sergey.taxiservice.models.geo.api;

import com.google.gson.annotations.SerializedName;

public class GeoObject {

    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}