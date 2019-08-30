package com.sergey.taxiservice.models.companion;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Companion implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("client_id")
    private int client_id;
    @SerializedName("lat")
    private float lat;
    @SerializedName("lng")
    private float lng;
    @SerializedName("lat_target")
    private float latTarget;
    @SerializedName("lng_target")
    private float lngTarget;
    @SerializedName("persons")
    private int persons;
    @SerializedName("address_data_from")
    private String fromAddress;
    @SerializedName("address_data_target")
    private String targetAddress;

    public Companion() {

    }

    protected Companion(Parcel in) {
        id = in.readInt();
        client_id = in.readInt();
        lat = in.readFloat();
        lng = in.readFloat();
        latTarget = in.readFloat();
        lngTarget = in.readFloat();
        persons = in.readInt();
        fromAddress = in.readString();
        targetAddress = in.readString();
    }

    public static final Creator<Companion> CREATOR = new Creator<Companion>() {
        @Override
        public Companion createFromParcel(Parcel in) {
            return new Companion(in);
        }

        @Override
        public Companion[] newArray(int size) {
            return new Companion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(client_id);
        dest.writeFloat(lat);
        dest.writeFloat(lng);
        dest.writeFloat(latTarget);
        dest.writeFloat(lngTarget);
        dest.writeInt(persons);
        dest.writeString(fromAddress);
        dest.writeString(targetAddress);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLatTarget() {
        return latTarget;
    }

    public void setLatTarget(float latTarget) {
        this.latTarget = latTarget;
    }

    public float getLngTarget() {
        return lngTarget;
    }

    public void setLngTarget(float lngTarget) {
        this.lngTarget = lngTarget;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }
}
