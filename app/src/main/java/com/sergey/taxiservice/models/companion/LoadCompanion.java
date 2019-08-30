package com.sergey.taxiservice.models.companion;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.BaseModel;

import java.util.List;

public class LoadCompanion extends BaseModel {

    @SerializedName("users")
    private List<CompanionWithInfo> users;

    public List<CompanionWithInfo> getUsers() {
        return users;
    }

    public void setUsers(List<CompanionWithInfo> users) {
        this.users = users;
    }
}
