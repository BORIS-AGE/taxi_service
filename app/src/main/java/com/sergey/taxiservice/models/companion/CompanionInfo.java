package com.sergey.taxiservice.models.companion;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CompanionInfo implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("client_id")
    private int client_id;
    @SerializedName("request_id")
    private int request_id;
    @SerializedName("state")
    private int state;
    @SerializedName("client_request_id")
    private int client_request_id;
    @SerializedName("client_request")
    private CompanionWithInfo request;

    protected CompanionInfo(Parcel in) {
        id = in.readInt();
        client_id = in.readInt();
        request_id = in.readInt();
        state = in.readInt();
        client_request_id = in.readInt();
        request = in.readParcelable(CompanionWithInfo.class.getClassLoader());
    }

    public static final Creator<CompanionInfo> CREATOR = new Creator<CompanionInfo>() {
        @Override
        public CompanionInfo createFromParcel(Parcel in) {
            return new CompanionInfo(in);
        }

        @Override
        public CompanionInfo[] newArray(int size) {
            return new CompanionInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(client_id);
        dest.writeInt(request_id);
        dest.writeInt(state);
        dest.writeInt(client_request_id);
        dest.writeParcelable(request, flags);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getClient_request_id() {
        return client_request_id;
    }

    public void setClient_request_id(int client_request_id) {
        this.client_request_id = client_request_id;
    }

    public CompanionWithInfo getRequest() {
        return request;
    }

    public void setRequest(CompanionWithInfo request) {
        this.request = request;
    }
}
