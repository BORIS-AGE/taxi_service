package com.sergey.taxiservice.routers.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.sergey.taxiservice.manager.preferences.PreferencesManager;

public class BaseRouter<A extends AppCompatActivity> {

    protected A activity;
    protected PreferencesManager manager;

    public BaseRouter(A activity, PreferencesManager manager) {
        this.activity = activity;
        this.manager = manager;
    }

    protected boolean isCurrentFragment(@IdRes int containerId, Class clazz) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(containerId);
        return clazz.isInstance(fragment);
    }

    protected void replaceFragment(@IdRes int containerId, Fragment fragment) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }
}
