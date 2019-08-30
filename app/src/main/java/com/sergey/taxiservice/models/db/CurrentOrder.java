package com.sergey.taxiservice.models.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentOrder extends RealmObject {

    @PrimaryKey
    private int id = 1; // not change this value
    private int type;
    private int state;
    private String rideInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRideInfo() {
        return rideInfo;
    }

    public void setRideInfo(String rideInfo) {
        this.rideInfo = rideInfo;
    }
}
