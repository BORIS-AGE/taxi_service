package com.sergey.taxiservice.models.share;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.UserModel;

public class FeedbackModel {

    @SerializedName("id")
    private int id;
    @SerializedName("client_id")
    private int clientId;
    @SerializedName("receiver_id")
    private int receiverId;
    @SerializedName("punctuality")
    private float punctuality;
    @SerializedName("sociability")
    private float sociability;
    @SerializedName("recommend")
    private float recommend;
    @SerializedName("not_recommend")
    private float not_recommend;
    @SerializedName("sender")
    private UserModel sender;

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

    public float getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(float punctuality) {
        this.punctuality = punctuality;
    }

    public float getSociability() {
        return sociability;
    }

    public void setSociability(float sociability) {
        this.sociability = sociability;
    }

    public float getRecommend() {
        return recommend;
    }

    public void setRecommend(float recommend) {
        this.recommend = recommend;
    }

    public float getNot_recommend() {
        return not_recommend;
    }

    public void setNot_recommend(float not_recommend) {
        this.not_recommend = not_recommend;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }
}
