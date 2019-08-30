package com.sergey.taxiservice.di.modules;

import com.sergey.taxiservice.di.scopes.ActivityScope;
import com.sergey.taxiservice.di.scopes.FragmentScope;
import com.sergey.taxiservice.manager.location.LocationManager;
import com.sergey.taxiservice.manager.location.LocationManagerImpl;
import com.sergey.taxiservice.routers.main.MainRouter;
import com.sergey.taxiservice.routers.main.MainRouterImpl;
import com.sergey.taxiservice.ui.fragments.home.view.HomeFragment;
import com.sergey.taxiservice.ui.fragments.map.MapFragment;
import com.sergey.taxiservice.ui.fragments.trip.companion.view.CompanionFragment;
import com.sergey.taxiservice.ui.fragments.user.info.brief.view.UserInfoFragment;
import com.sergey.taxiservice.ui.fragments.user.settings.view.SettingsFragment;
import com.sergey.taxiservice.ui.fragments.trip.completion.view.TripCompletionFragment;
import com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment;
import com.sergey.taxiservice.ui.fragments.trip.execution.view.TripExecutionFragment;
import com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainActivityModule {

    @ActivityScope
    @Binds
    MainRouter mainRouter(MainRouterImpl mainRouter);

    @ActivityScope
    @Binds
    LocationManager locationManager(LocationManagerImpl locationManager);

    @FragmentScope
    @ContributesAndroidInjector
    HomeFragment homeFragment();

    @FragmentScope
    @ContributesAndroidInjector
    TripCompletionFragment tripComplationFragment();

    @FragmentScope
    @ContributesAndroidInjector
    TripConfirmationFragment tripConfirmationFragment();

    @FragmentScope
    @ContributesAndroidInjector
    TripExecutionFragment tripExecutionFragment();

    @FragmentScope
    @ContributesAndroidInjector
    UserFullInfoFragment userFullInfoFragment();

    @FragmentScope
    @ContributesAndroidInjector
    SettingsFragment settingsFragment();

    @FragmentScope
    @ContributesAndroidInjector
    CompanionFragment companionFragment();

    @FragmentScope
    @ContributesAndroidInjector
    MapFragment mapFragment();

    @FragmentScope
    @ContributesAndroidInjector
    UserInfoFragment userInfoFragment();
}
