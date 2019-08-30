package com.sergey.taxiservice.models.ride;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ride implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("client_id")
    private int clientId;
    @SerializedName("client_name")
    private String clientName;
    @SerializedName("client_phone_number")
    private String phoneNumber;
    @SerializedName("ride_data")
    private RideData rideData;
    @SerializedName("pay_type")
    private int pay_type;
    @SerializedName("car_type")
    private int car_type;

    public Ride() {

    }

    protected Ride(Parcel in) {
        id = in.readInt();
        clientId = in.readInt();
        clientName = in.readString();
        phoneNumber = in.readString();
        rideData = in.readParcelable(RideData.class.getClassLoader());
        pay_type = in.readInt();
        car_type = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RideData getRideData() {
        return rideData;
    }

    public void setRideData(RideData rideData) {
        this.rideData = rideData;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public void setCar_type(int car_type) {
        this.car_type = car_type;
    }

    public int getPay_type() {
        return pay_type;
    }

    public int getCar_type() {
        return car_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(clientId);
        parcel.writeString(clientName);
        parcel.writeString(phoneNumber);
        parcel.writeParcelable(rideData, i);
        parcel.writeInt(pay_type);
        parcel.writeInt(car_type);
    }

    public static final Creator<Ride> CREATOR = new Creator<Ride>() {
        @Override
        public Ride createFromParcel(Parcel in) {
            return new Ride(in);
        }

        @Override
        public Ride[] newArray(int size) {
            return new Ride[size];
        }
    };
}
