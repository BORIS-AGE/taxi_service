package com.sergey.taxiservice.models.companion;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.client.Client;

public class Route implements Parcelable {

    @SerializedName("lat")
    private float lat;
    @SerializedName("lng")
    private float lng;
    @SerializedName("lat_target")
    private float latTarget;
    @SerializedName("lng_target")
    private float lngTarget;
    @SerializedName("address_data_from")
    private String fromAddress;
    @SerializedName("address_data_target")
    private String targetAddress;
    @SerializedName("client")
    private Client client;

    public Route() {

    }

    protected Route(Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
        latTarget = in.readFloat();
        lngTarget = in.readFloat();
        fromAddress = in.readString();
        targetAddress = in.readString();
        client = in.readParcelable(Client.class.getClassLoader());
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lat);
        dest.writeFloat(lng);
        dest.writeFloat(latTarget);
        dest.writeFloat(lngTarget);
        dest.writeString(fromAddress);
        dest.writeString(targetAddress);
        dest.writeParcelable(client, flags);
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
