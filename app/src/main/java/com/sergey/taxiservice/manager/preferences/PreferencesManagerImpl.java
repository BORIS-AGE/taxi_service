package com.sergey.taxiservice.manager.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sergey.taxiservice.models.UserModel;

public class PreferencesManagerImpl implements PreferencesManager {

    private static final String PREFERENCES = "preferences";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String USER_MODEL = "USER_MODEL";

    private SharedPreferences preferences;

    public PreferencesManagerImpl(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public String getToken() {
        return preferences.getString(ACCESS_TOKEN, null);
    }

    @Override
    public UserModel getUser() {
        return new Gson().fromJson(preferences.getString(USER_MODEL, ""), UserModel.class);
    }

    @Override
    public void clearPreferences() {
        preferences.edit().clear().apply();
    }

    @Override
    public void saveToken(String token) {
        preferences.edit().putString(ACCESS_TOKEN, token).apply();
    }

    @Override
    public void saveUser(UserModel userModel) {
        preferences.edit().putString(USER_MODEL, new Gson().toJson(userModel)).apply();
    }
}
