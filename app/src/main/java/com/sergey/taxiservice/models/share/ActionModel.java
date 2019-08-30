package com.sergey.taxiservice.models.share;

import com.google.gson.annotations.SerializedName;

public class ActionModel {

    @SerializedName("id")
    private int id;
    @SerializedName("client_id")
    private int clientId;
    @SerializedName("receiver_id")
    private int receiverId;

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

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
