package com.sergey.taxiservice.models.geo.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeoStreets {

    @SerializedName("geo_street")
    private List<GeoStreet> geoStreet;

    public List<GeoStreet> getGeoStreet() {
        return geoStreet;
    }

    public void setGeoStreet(List<GeoStreet> geoStreet) {
        this.geoStreet = geoStreet;
    }
}