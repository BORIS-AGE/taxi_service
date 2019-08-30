package com.sergey.taxiservice.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.manager.location.LocationManager;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.routers.main.MainRouter;
import com.sergey.taxiservice.ui.base.BaseActivity;
import com.sergey.taxiservice.ui.views.NavigationViewHeader;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static void open(Activity activity, Boolean clearBackStack){
        Intent launchIntent = new Intent(activity, MainActivity.class);
        if(clearBackStack)
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(launchIntent);
    }

    @Inject
    MainRouter mainRouter;

    @Inject
    PreferencesManager manager;

    @Inject
    LocationManager locationManager;

    private int currentNavigationItem;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private NavigationViewHeader navigationHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setupNavigationDrawer();
        mainRouter.showHomeScreen();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.init();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                mainRouter.showHomeScreen();
                break;

            case R.id.nav_create_order:
                mainRouter.showSingleTripConfirmationScreen();
                break;

            case R.id.nav_settings:
                mainRouter.showSettingsScreen();
                break;

            case R.id.nav_current_ride:
                mainRouter.showCurrentRideScreen();
                break;

            case R.id.nav_find_companion:
                mainRouter.showTripWithCompanyConfirmationScreen();
                break;

            case R.id.nav_logout:
                manager.clearPreferences();
                LoginActivity.open(this);
                break;
        }

        drawer.closeDrawers();
        return false;
    }

    private void setupActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void setupNavigationDrawer() {
        int openDrawerStr = R.string.navigation_drawer_open;
        int closeDrawerStr = R.string.navigation_drawer_close;

        drawer = findViewById(R.id.drawer_layout);
        navigation = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, openDrawerStr, closeDrawerStr) {
            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (!drawer.isDrawerOpen(GravityCompat.START)) {
                        navigationHeader.setUser(manager.getUser());
                    }

                    invalidateOptionsMenu();
                }
            }

        };

        navigationHeader = new NavigationViewHeader(this);
        navigationHeader.setUser(manager.getUser());
        navigationHeader.setOnClickListener(v -> {
            mainRouter.showMyAccountScreen();
            drawer.closeDrawers();
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigation.setNavigationItemSelectedListener(this);
        navigation.addHeaderView(navigationHeader);
    }

    public void setNavigationItemSelected(@IdRes int itemId) {
        currentNavigationItem = itemId;

        if(itemId != R.id.nav_view) {
            navigation.setCheckedItem(itemId);
        } else {
            int size = navigation.getMenu().size();
            for (int i = 0; i < size; i++) {
                navigation.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    public int getNavigationItemSelected() {
        return currentNavigationItem;
    }
}
