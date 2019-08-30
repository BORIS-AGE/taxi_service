package com.sergey.taxiservice.di.modules;

import com.sergey.taxiservice.di.scopes.ActivityScope;
import com.sergey.taxiservice.di.scopes.FragmentScope;
import com.sergey.taxiservice.routers.login.LoginRouter;
import com.sergey.taxiservice.routers.login.LoginRouterImpl;
import com.sergey.taxiservice.ui.fragments.setup.login.view.LoginFragment;
import com.sergey.taxiservice.ui.fragments.setup.registration.view.RegistrationFragment;
import com.sergey.taxiservice.ui.fragments.setup.verification.view.VerificationFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface LoginActivityModule {

    @ActivityScope
    @Binds
    LoginRouter loginRouter(LoginRouterImpl loginRouter);

    @FragmentScope
    @ContributesAndroidInjector
    LoginFragment loginFragment();

    @FragmentScope
    @ContributesAndroidInjector
    RegistrationFragment registrationFragment();

    @FragmentScope
    @ContributesAndroidInjector
    VerificationFragment verificationFragment();
}
