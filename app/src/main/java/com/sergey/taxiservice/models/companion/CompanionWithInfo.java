package com.sergey.taxiservice.models.companion;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.models.client.Client;

public class CompanionWithInfo extends Companion {

    @SerializedName("client")
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CompanionWithInfo() {

    }

    protected CompanionWithInfo(Parcel in) {
        super(in);
        client = in.readParcelable(CompanionWithInfo.class.getClassLoader());
    }

    public static final Creator<CompanionWithInfo> CREATOR = new Creator<CompanionWithInfo>() {
        @Override
        public CompanionWithInfo createFromParcel(Parcel in) {
            return new CompanionWithInfo(in);
        }

        @Override
        public CompanionWithInfo[] newArray(int size) {
            return new CompanionWithInfo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(client, flags);
    }
}
