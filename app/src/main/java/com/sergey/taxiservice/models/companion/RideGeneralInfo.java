package com.sergey.taxiservice.models.companion;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RideGeneralInfo extends CompanionWithInfo {

    @SerializedName("ride_price")
    private int ridePrice;
    @SerializedName("ride_duration")
    private int rideDuration;
    @SerializedName("routes")
    private List<Route> routes;
    @SerializedName("companions")
    private List<CompanionInfo> companions;

    public RideGeneralInfo() {

    }

    protected RideGeneralInfo(Parcel in) {
        super(in);
        ridePrice = in.readInt();
        rideDuration = in.readInt();
        routes = in.createTypedArrayList(Route.CREATOR);
        companions = in.createTypedArrayList(CompanionInfo.CREATOR);
    }

    public static final Creator<RideGeneralInfo> CREATOR = new Creator<RideGeneralInfo>() {
        @Override
        public RideGeneralInfo createFromParcel(Parcel in) {
            return new RideGeneralInfo(in);
        }

        @Override
        public RideGeneralInfo[] newArray(int size) {
            return new RideGeneralInfo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(ridePrice);
        dest.writeInt(rideDuration);
        dest.writeTypedList(routes);
        dest.writeTypedList(companions);
    }

    public int getRidePrice() {
        return ridePrice;
    }

    public void setRidePrice(int ridePrice) {
        this.ridePrice = ridePrice;
    }

    public int getRideDuration() {
        return rideDuration;
    }

    public void setRideDuration(int rideDuration) {
        this.rideDuration = rideDuration;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<CompanionInfo> getCompanions() {
        return companions;
    }

    public void setCompanions(List<CompanionInfo> companions) {
        this.companions = companions;
    }
}
