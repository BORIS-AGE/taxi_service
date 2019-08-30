package com.sergey.taxiservice.models.geo.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeoStreet {

    @SerializedName("name")
    private String name;
    @SerializedName("houses")
    private List<GeoHouse> houses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeoHouse> getHouses() {
        return houses;
    }

    public void setHouses(List<GeoHouse> houses) {
        this.houses = houses;
    }
}