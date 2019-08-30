package com.sergey.taxiservice.models.ride;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.GeoAddress;

public class RideData implements Parcelable {

    @SerializedName("order_cost")
    private int orderCost;
    @SerializedName("route_address_to")
    private GeoAddress routeAddressTo;
    @SerializedName("route_address_from")
    private GeoAddress routeAddressFrom;

    public RideData() {

    }

    protected RideData(Parcel in) {
        orderCost = in.readInt();
        routeAddressTo = in.readParcelable(GeoAddress.class.getClassLoader());
        routeAddressFrom = in.readParcelable(GeoAddress.class.getClassLoader());
    }

    public int getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(int orderCost) {
        this.orderCost = orderCost;
    }

    public GeoAddress getRouteAddressTo() {
        return routeAddressTo;
    }

    public void setRouteAddressTo(GeoAddress routeAddressTo) {
        this.routeAddressTo = routeAddressTo;
    }

    public GeoAddress getRouteAddressFrom() {
        return routeAddressFrom;
    }

    public void setRouteAddressFrom(GeoAddress routeAddressFrom) {
        this.routeAddressFrom = routeAddressFrom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderCost);
        parcel.writeParcelable(routeAddressTo, i);
        parcel.writeParcelable(routeAddressFrom, i);
    }

    public static final Creator<RideData> CREATOR = new Creator<RideData>() {
        @Override
        public RideData createFromParcel(Parcel in) {
            return new RideData(in);
        }

        @Override
        public RideData[] newArray(int size) {
            return new RideData[size];
        }
    };
}
