package com.sergey.taxiservice.models.geo.db;

import com.sergey.taxiservice.models.geo.api.GeoObject;
import com.sergey.taxiservice.models.geo.api.GeoStreet;

import io.realm.RealmObject;

public class GeoDbObject extends RealmObject {

    private double lat;
    private double lng;
    private String data;
    private String additionalData;

    public GeoDbObject() {

    }

    public GeoDbObject(GeoObject geoObject) {

    }

    public GeoDbObject(GeoStreet geoStreet) {

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
