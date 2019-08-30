package com.sergey.taxiservice.models.share;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;

public class InviteModel extends BaseModel {

    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
