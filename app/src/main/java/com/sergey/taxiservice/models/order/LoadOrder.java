package com.sergey.taxiservice.models.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;
import com.sergey.taxiservice.models.ride.Evos;
import com.sergey.taxiservice.models.ride.Ride;

public class LoadOrder extends BaseModel implements Parcelable {

    @SerializedName("ride")
    private Ride ride;
    @SerializedName("evos")
    private Evos evos;

    public LoadOrder() {

    }

    protected LoadOrder(Parcel in) {
        ride = in.readParcelable(Ride.class.getClassLoader());
        evos = in.readParcelable(Evos.class.getClassLoader());
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Evos getEvos() {
        return evos;
    }

    public void setEvos(Evos evos) {
        this.evos = evos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(ride, i);
        parcel.writeParcelable(evos, i);
    }

    public static final Creator<LoadOrder> CREATOR = new Creator<LoadOrder>() {
        @Override
        public LoadOrder createFromParcel(Parcel in) {
            return new LoadOrder(in);
        }

        @Override
        public LoadOrder[] newArray(int size) {
            return new LoadOrder[size];
        }
    };
}
