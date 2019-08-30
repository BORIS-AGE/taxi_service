package com.sergey.taxiservice.models.geo.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeoObjects {

    @SerializedName("geo_object")
    private List<GeoObject> geoObject;

    public List<GeoObject> getGeoObject() {
        return geoObject;
    }

    public void setGeoObject(List<GeoObject> geoObject) {
        this.geoObject = geoObject;
    }
}