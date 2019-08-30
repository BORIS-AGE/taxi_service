package com.sergey.taxiservice.models.client;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Client implements Parcelable {

    @SerializedName("id")
    private  int id;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String firstName;
    @SerializedName("surname")
    private String lastName;
    @SerializedName("patronymic")
    private String patronymic;
    @SerializedName("verificated")
    private int verificated;
    @SerializedName("gender")
    private int gender;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("picture")
    private String avatar;

    public Client() {

    }

    protected Client(Parcel in) {
        id = in.readInt();
        phoneNumber = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        patronymic = in.readString();
        verificated = in.readInt();
        gender = in.readInt();
        birthday = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getVerificated() {
        return verificated;
    }

    public void setVerificated(int verificated) {
        this.verificated = verificated;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(patronymic);
        dest.writeInt(verificated);
        dest.writeInt(gender);
        dest.writeString(birthday);
        dest.writeString(avatar);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
