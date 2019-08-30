package com.sergey.taxiservice.ui.fragments.setup.login.view;

import com.sergey.taxiservice.ui.base.BaseView;

public interface LoginView extends BaseView {

    String getPhoneNumber();

    String getPassword();

    void onPhoneNumberError();

    void onPasswordError();

    void onAuthSuccess();
}
