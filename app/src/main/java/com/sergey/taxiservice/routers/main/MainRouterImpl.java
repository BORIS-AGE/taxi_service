package com.sergey.taxiservice.routers.main;

import com.google.gson.Gson;
import com.sergey.taxiservice.R;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.models.companion.Companion;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.routers.base.BaseRouter;
import com.sergey.taxiservice.ui.activities.MainActivity;
import com.sergey.taxiservice.ui.fragments.home.view.HomeFragment;
import com.sergey.taxiservice.ui.fragments.trip.companion.view.CompanionFragment;
import com.sergey.taxiservice.ui.fragments.user.settings.view.SettingsFragment;
import com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment;
import com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment;

import javax.inject.Inject;

import static com.sergey.taxiservice.ui.fragments.trip.companion.view.CompanionFragment.COMPANION_INFO;
import static com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment.RIDE_WITH_COMPANY;
import static com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment.SINGLE_RIDE;
import static com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment.TRIP_TYPE;
import static com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment.USER_ID;

public class MainRouterImpl extends BaseRouter<MainActivity> implements MainRouter {

    @Inject
    MainRouterImpl(MainActivity activity, PreferencesManager manager) {
        super(activity, manager);
    }

    @Override
    public void showHomeScreen() {
        if(!isCurrentFragment(R.id.fragment_container, HomeFragment.class)) {
            replaceFragment(R.id.fragment_container, new HomeFragment());
            activity.setNavigationItemSelected(R.id.nav_home);
        }
    }

    @Override
    public void showSettingsScreen() {
        if(!isCurrentFragment(R.id.fragment_container, SettingsFragment.class)) {
            replaceFragment(R.id.fragment_container, new SettingsFragment());
            activity.setNavigationItemSelected(R.id.nav_settings);
        }
    }

    @Override
    public void showSingleTripConfirmationScreen() {
        if(activity.getNavigationItemSelected() != R.id.nav_create_order) {

            if(isCurrentOrderNotExist()) {
                TripConfirmationFragment fragment = new TripConfirmationFragment();
                fragment.addToArguments(TRIP_TYPE, SINGLE_RIDE);
                replaceFragment(R.id.fragment_container, fragment);
                activity.setNavigationItemSelected(R.id.nav_create_order);

            } else activity.showMessage("У вас уже есть текущая поездка");
        }
    }

    @Override
    public void showCurrentRideScreen() {
        if(isCurrentOrderNotExist()) {
            activity.showMessage("У вас нет текущей поездки");
            showHomeScreen();

        } else {
            CurrentOrder currentOrder = TaxiApplication.getDbInstance().where(CurrentOrder.class).findFirst();
            if(!isCurrentFragment(R.id.fragment_container, CompanionFragment.class)
                    && currentOrder != null && currentOrder.getState() == OrderState.SEARCH_COMPANION) {
                Companion companion = new Gson().fromJson(currentOrder.getRideInfo(), Companion.class);

                CompanionFragment fragment = new CompanionFragment();
                fragment.addToArguments(COMPANION_INFO, companion);
                replaceFragment(R.id.fragment_container, fragment);
            }

            activity.setNavigationItemSelected(R.id.nav_current_ride);
        }
    }

    @Override
    public void showTripWithCompanyConfirmationScreen() {
        if(activity.getNavigationItemSelected() != R.id.nav_find_companion) {

            if(isCurrentOrderNotExist()) {
                TripConfirmationFragment fragment = new TripConfirmationFragment();
                fragment.addToArguments(TRIP_TYPE, RIDE_WITH_COMPANY);
                replaceFragment(R.id.fragment_container, fragment);
                activity.setNavigationItemSelected(R.id.nav_find_companion);

            } else activity.showMessage("У вас уже есть текущая поездка");
        }
    }

    @Override
    public void showMyAccountScreen() {
        UserModel user = manager.getUser();
        if(user != null) {
            showUserFullInfoScreen(user.getId());
            activity.setNavigationItemSelected(R.id.nav_view);
        }
    }

    @Override
    public void showUserFullInfoScreen(int userId) {
        if(!isCurrentFragment(R.id.fragment_container, UserFullInfoFragment.class)) {
            UserFullInfoFragment fragment = new UserFullInfoFragment();
            fragment.addToArguments(USER_ID, userId);
            replaceFragment(R.id.fragment_container, fragment);
        }
    }

    private boolean isCurrentOrderNotExist() {
        CurrentOrder currentOrder = TaxiApplication.getDbInstance().where(CurrentOrder.class).findFirst();
        return currentOrder == null;
    }
}
