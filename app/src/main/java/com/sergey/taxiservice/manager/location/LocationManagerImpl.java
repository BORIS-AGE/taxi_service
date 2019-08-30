package com.sergey.taxiservice.manager.location;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.manager.location.listener.OnLocationChangeListener;
import com.sergey.taxiservice.ui.activities.MainActivity;

import javax.inject.Inject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

public class LocationManagerImpl implements LocationManager, LocationListener {

    private Location location;
    private MainActivity activity;
    private OnLocationChangeListener listener;

    @Inject
    LocationManagerImpl(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void init() {
        if(activity == null)
            return;

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
        }

        if (ActivityCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            return;
        }

        android.location.LocationManager service = (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (service != null && !service.isProviderEnabled(GPS_PROVIDER))
            new AlertDialog.Builder(activity)
                    .setTitle("Включите GPS, чтобы увидеть вашу позицию")
                    .setMessage("Включить GPS?")
                    .setCancelable(false)
                    .setPositiveButton(activity.getString(R.string.yes), (dialog, id) -> activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton(activity.getString(R.string.no), (dialog, id) -> dialog.cancel())
                    .create()
                    .show();

        android.location.LocationManager locationManager = (android.location.LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if(locationManager != null) {
            locationManager.requestLocationUpdates(GPS_PROVIDER, 5000, 10, this);
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 5000, 10, this);

            Location lastKnownGPSLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
            Location lastKnownNetworkLocation = locationManager.getLastKnownLocation(NETWORK_PROVIDER);

            if(lastKnownGPSLocation != null) {
                onLocationChanged(lastKnownGPSLocation);
            } else if(lastKnownNetworkLocation != null) {
                onLocationChanged(lastKnownNetworkLocation);
            } else {
                onLocationChanged(defaultLocation());
            }
        }
    }

    @Override
    public void setChangeListener(OnLocationChangeListener listener) {
        this.listener = listener;

        if(location != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

        if(listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}


    private Location defaultLocation() {
        Location location = new Location(GPS_PROVIDER);
        location.setLatitude(50.45466);
        location.setLongitude(30.5238);

        return location;
    }
}
