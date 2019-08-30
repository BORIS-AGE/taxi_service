package com.sergey.taxiservice.models.geo.api;

import com.google.gson.annotations.SerializedName;

public class GeoResponse {

    @SerializedName("geo_streets")
    private GeoStreets geoStreets;
    @SerializedName("geo_objects")
    private GeoObjects geoObjects;

    public GeoStreets getGeoStreets() {
        return geoStreets;
    }

    public void setGeoStreets(GeoStreets geoStreets) {
        this.geoStreets = geoStreets;
    }

    public GeoObjects getGeoObjects() {
        return geoObjects;
    }

    public void setGeoObjects(GeoObjects geoObjects) {
        this.geoObjects = geoObjects;
    }
}