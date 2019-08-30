package com.sergey.taxiservice.routers.login;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.routers.base.BaseRouter;
import com.sergey.taxiservice.ui.activities.LoginActivity;
import com.sergey.taxiservice.ui.fragments.setup.login.view.LoginFragment;
import com.sergey.taxiservice.ui.fragments.setup.registration.view.RegistrationFragment;
import com.sergey.taxiservice.ui.fragments.setup.verification.view.VerificationFragment;

import javax.inject.Inject;

import static com.sergey.taxiservice.ui.fragments.setup.registration.view.RegistrationFragment.EXTRA_PASSWORD;
import static com.sergey.taxiservice.ui.fragments.setup.registration.view.RegistrationFragment.EXTRA_PHONE_NUMBER;

public class LoginRouterImpl extends BaseRouter<LoginActivity> implements LoginRouter {

    @Inject
    LoginRouterImpl(LoginActivity activity, PreferencesManager manager) {
        super(activity, manager);
    }

    @Override
    public void showLoginScreen() {
        if(!isCurrentFragment(R.id.fragment_container, LoginFragment.class)) {
            replaceFragment(R.id.fragment_container, new LoginFragment());
        }
    }

    @Override
    public void showVerificationScreen() {
        if(!isCurrentFragment(R.id.fragment_container, VerificationFragment.class)) {
            replaceFragment(R.id.fragment_container, new VerificationFragment());
        }
    }

    @Override
    public void showRegistrationScreen(String phoneNumber, String password) {
        if(!isCurrentFragment(R.id.fragment_container, RegistrationFragment.class)) {
            RegistrationFragment fragment = new RegistrationFragment();
            fragment.addToArguments(EXTRA_PHONE_NUMBER, phoneNumber);
            fragment.addToArguments(EXTRA_PASSWORD, password);

            replaceFragment(R.id.fragment_container, fragment);
        }
    }
}
