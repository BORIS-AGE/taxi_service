package com.sergey.taxiservice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.routers.login.LoginRouter;
import com.sergey.taxiservice.ui.base.BaseActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    public static void open(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    @Inject
    LoginRouter router;

    @Inject
    PreferencesManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(manager.getToken() == null) {
            router.showLoginScreen();
            return;
        }

        UserModel user = manager.getUser();
        if(user != null && user.getVerificated() != 1) {
            router.showVerificationScreen();
            return;
        }

        MainActivity.open(this, true);
        finish();
    }
}
