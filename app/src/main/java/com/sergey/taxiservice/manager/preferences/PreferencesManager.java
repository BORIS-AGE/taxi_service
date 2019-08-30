package com.sergey.taxiservice.manager.preferences;

import com.sergey.taxiservice.models.UserModel;

public interface PreferencesManager {

    String getToken();

    UserModel getUser();

    void clearPreferences();

    void saveToken(String token);

    void saveUser(UserModel userModel);
}
