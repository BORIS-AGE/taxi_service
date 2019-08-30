package com.sergey.taxiservice.models.ride;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Evos implements Parcelable {

    @SerializedName("order_cost")
    private int orderCost;
    @SerializedName("order_car_info")
    private String orderCarInfo;
    @SerializedName("driver_phone")
    private String driverPhone;
    @SerializedName("required_time")
    private String requiredTime;
    @SerializedName("order_is_archive")
    private boolean isArrchived;

    public Evos() {

    }

    protected Evos(Parcel in) {
        orderCost = in.readInt();
        orderCarInfo = in.readString();
        driverPhone = in.readString();
        requiredTime = in.readString();
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderCarInfo() {
        return orderCarInfo;
    }

    public void setOrderCarInfo(String orderCarInfo) {
        this.orderCarInfo = orderCarInfo;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public boolean isArrchived() {
        return isArrchived;
    }

    public void setArrchived(boolean arrchived) {
        isArrchived = arrchived;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderCost);
        parcel.writeString(orderCarInfo);
        parcel.writeString(driverPhone);
        parcel.writeString(requiredTime);
    }

    public static final Creator<Evos> CREATOR = new Creator<Evos>() {
        @Override
        public Evos createFromParcel(Parcel in) {
            return new Evos(in);
        }

        @Override
        public Evos[] newArray(int size) {
            return new Evos[size];
        }
    };
}
