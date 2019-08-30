package com.sergey.taxiservice.ui.fragments.user.settings.view;

import com.sergey.taxiservice.ui.base.BaseView;

import org.json.JSONObject;

public interface SettingsView extends BaseView {

    String getPhoneNumber();

    String getPassword();

    String getFirstName();

    String getLastName();

    String getPatronymic();

    String getAbout();

    int getAge();

    int getGender();

    void setPhoneNumber(String phoneNumber);

    void setPassword(String password);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setPatronymic(String patronymic);

    void setAbout(String about);

    void setAge(int age);

    void setGender(int gender);

    void setAvatar(String avatar);

    void onPhoneNumberError();

    void onPasswordError();

    void onFirstNameError();

    void onLastNameError();

    void onAvaDeleted();

    void onEditSuccess();

    void onEditFailed(JSONObject errors);
}
