package com.sergey.taxiservice.routers.login;

public interface LoginRouter {

    void showLoginScreen();

    void showVerificationScreen();

    void showRegistrationScreen(String phoneNumber, String password);
}
