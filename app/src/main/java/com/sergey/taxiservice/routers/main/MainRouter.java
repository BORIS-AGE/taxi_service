package com.sergey.taxiservice.routers.main;

public interface MainRouter {

    void showHomeScreen();

    void showSettingsScreen();

    void showSingleTripConfirmationScreen();

    void showCurrentRideScreen();

    void showTripWithCompanyConfirmationScreen();

    void showMyAccountScreen();

    void showUserFullInfoScreen(int userId);
}
