package com.sergey.taxiservice.ui.fragments.setup.registration.view;

import com.sergey.taxiservice.ui.base.BaseView;

import org.json.JSONObject;

public interface RegistrationView extends BaseView {

    String getPhoneNumber();

    String getPassword();

    String getConfirmPassword();

    String getEmail();

    String getFirstName();

    String getLastName();

    void onPhoneNumberError();

    void onPasswordError();

    void onConfirmPasswordError();

    void onEmailError();

    void onFirstNameError();

    void onLastNameError();

    void onRegistrationFailed(JSONObject errors);
}
