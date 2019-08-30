package com.sergey.taxiservice.models.geo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class AddressModel implements Parcelable {

    private double lat;
    private double lon;
    private String data;
    private String additionalData;

    public AddressModel() {}

    protected AddressModel(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
        data = in.readString();
        additionalData = in.readString();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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

    public String getTitle() {
        String title = "";

        if(getData() != null && !getData().isEmpty()) {
            title += getData();
        }

        if(getAdditionalData() != null && !getAdditionalData().isEmpty()) {
            title += ", " + getAdditionalData();
        }

        return title;
    }

    public Map<String, Object> convertToStartRemoteModel() {
        Map<String, Object> object = new HashMap<>();
        object.put("address_data_from", getTitle());
        object.put("lat", getLat());
        object.put("lng", getLon());

        return object;
    }

    public Map<String, Object> convertToFinishRemoteModel() {
        Map<String, Object> object = new HashMap<>();
        object.put("address_data_target", getTitle());
        object.put("lat_target", getLat());
        object.put("lng_target", getLon());

        return object;
    }

    Map<String, Object> convertToRemoteModel() {
        Map<String, Object> object = new HashMap<>();
        object.put("name", getTitle());
        object.put("lat", getLat());
        object.put("lng", getLon());

        return object;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(data);
        dest.writeString(additionalData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
