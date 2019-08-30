package com.sergey.taxiservice.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GeoAddress implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("number")
    private int number;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;

    public GeoAddress() {

    }

    protected GeoAddress(Parcel in) {
        name = in.readString();
        number = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(number);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }

    public static final Creator<GeoAddress> CREATOR = new Creator<GeoAddress>() {
        @Override
        public GeoAddress createFromParcel(Parcel in) {
            return new GeoAddress(in);
        }

        @Override
        public GeoAddress[] newArray(int size) {
            return new GeoAddress[size];
        }
    };
}
