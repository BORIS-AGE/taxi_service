package com.sergey.taxiservice.models;

import com.google.gson.annotations.SerializedName;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo.UserGeneralInfo;
import com.sergey.taxiservice.util.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserModel {

    @SerializedName("id")
    private  int id;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String firstName;
    @SerializedName("surname")
    private String lastName;
    @SerializedName("patronymic")
    private String patronymic;
    @SerializedName("gender")
    private int gender;
    @SerializedName("picture")
    private String avatar;
    @SerializedName("verificated")
    private int verificated;
    @SerializedName("age")
    private int age;
    @SerializedName("about")
    private String about;

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getGender() {
        return gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getVerificated() {
        return verificated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setVerificated(int verificated) {
        this.verificated = verificated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public UserGeneralInfo getGeneralInfo() {
        String fullName = "";
        String about = "";
        String avaPath = "";

        if(getLastName() != null && !getLastName().isEmpty()) {
            fullName += getLastName();
        }

        if(getFirstName() != null && !getFirstName().isEmpty()) {
            fullName += " " + getFirstName();
        }

        if(getPatronymic() != null && !getPatronymic().isEmpty()) {
            fullName += " " + getPatronymic();
        }

        if(getAbout() != null && !getAbout().isEmpty()) {
            about = getAbout();
        }

        if(getAvatar() != null && !getAvatar().isEmpty()) {
            avaPath = getAvatar();
        }

        return new UserGeneralInfo(fullName, avaPath, about, getGender(), getAge());
    }

    public Map<String, RequestBody> toRemoteModel() {
        Map<String, RequestBody> params = new HashMap<>();
        params.put("name", RequestBody.create(MediaType.parse("text/plain"), getFirstName()));
        params.put("surname", RequestBody.create(MediaType.parse("text/plain"), getLastName()));
        params.put("patronymic", RequestBody.create(MediaType.parse("text/plain"), getPatronymic()));
        params.put("password", RequestBody.create(MediaType.parse("text/plain"), getPassword()));
        params.put("phone_number", RequestBody.create(MediaType.parse("text/plain"), getPhoneNumber()));
        params.put("gender", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(getGender())));
        params.put("birthday", RequestBody.create(MediaType.parse("text/plain"), TimeUtils.calculateBirthDay(getAge())));
        params.put("about", RequestBody.create(MediaType.parse("text/plain"), getAbout()));

        return params;
    }
}
