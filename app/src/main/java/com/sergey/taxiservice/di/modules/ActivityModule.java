package com.sergey.taxiservice.di.modules;

import com.sergey.taxiservice.di.scopes.FragmentScope;
import com.sergey.taxiservice.ui.fragments.car.view.CarFoundedFragment;
import com.sergey.taxiservice.ui.fragments.share.action.view.ActionFragment;
import com.sergey.taxiservice.ui.fragments.share.approve.view.ApproveFragment;
import com.sergey.taxiservice.ui.fragments.share.chat.view.ChatFragment;
import com.sergey.taxiservice.ui.fragments.details.view.CompanionDetailsFragment;
import com.sergey.taxiservice.ui.fragments.driver.view.DriverSearchingFragment;
import com.sergey.taxiservice.ui.fragments.search.entire.view.AddressEntireFragment;
import com.sergey.taxiservice.ui.fragments.trip.invite.view.InvitePersonFragment;
import com.sergey.taxiservice.ui.fragments.trip.services.view.AdditionalServicesFragment;
import com.sergey.taxiservice.ui.fragments.search.input.view.StreetSearchFragment;
import com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    StreetSearchFragment streatSearchFragment();

    @FragmentScope
    @ContributesAndroidInjector
    AddressEntireFragment addressEntireFragment();

    @FragmentScope
    @ContributesAndroidInjector
    CarFoundedFragment carFoundedFragment();

    @FragmentScope
    @ContributesAndroidInjector
    DriverSearchingFragment driverSearchingFragment();

    @FragmentScope
    @ContributesAndroidInjector
    CompanionDetailsFragment companionDetailsFragment();

    @FragmentScope
    @ContributesAndroidInjector
    ChatFragment chatFragment();

    @FragmentScope
    @ContributesAndroidInjector
    AdditionalServicesFragment additionalServicesFragment();

    @FragmentScope
    @ContributesAndroidInjector
    InvitePersonFragment invitePersonFragment();

    @FragmentScope
    @ContributesAndroidInjector
    UserFullInfoFragment userFullInfoFragment();

    @FragmentScope
    @ContributesAndroidInjector
    ActionFragment actionFragment();

    @FragmentScope
    @ContributesAndroidInjector
    ApproveFragment approveFragment();
}
