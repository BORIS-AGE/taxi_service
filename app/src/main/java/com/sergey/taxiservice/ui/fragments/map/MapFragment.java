package com.sergey.taxiservice.ui.fragments.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sergey.taxiservice.R;
import com.sergey.taxiservice.manager.location.LocationManager;
import com.sergey.taxiservice.manager.location.listener.OnLocationChangeListener;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.companion.CompanionWithInfo;
import com.sergey.taxiservice.ui.views.LocationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MapFragment extends Fragment implements OnMapReadyCallback, OnLocationChangeListener, GoogleMap.OnMarkerClickListener {

    @Inject
    LocationManager locationManager;

    @Inject
    PreferencesManager preferencesManager;

    private boolean paintLocation = true;
    private float zoom = 11;
    private int gender = -1;

    private GoogleMap map;
    private Location location;

    // optional values
    private OnMarkerClickListener onMarkerClickListener;
    private List<CompanionWithInfo> markersInfo = null;
    private Map<Marker, CompanionWithInfo> markers = null;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, null);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        if(location != null) {
            moveMap(map, location);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.setChangeListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

        if(map != null) {
            moveMap(map, location);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(markers != null && marker != null && markers.containsKey(marker))
            onMarkerClickListener.onMarkerClicked(markers.get(marker));
        return true;
    }

    public void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        this.onMarkerClickListener = onMarkerClickListener;
    }

    public void setUserMarkers(List<CompanionWithInfo> users) {
        this.markersInfo = users;

        if(users != null)
            markers = new HashMap<>();
        else markers = null;

        onMapReady(map);
    }

    public void setPaintLocation(boolean paintLocation) {
        this.paintLocation = paintLocation;
    }

    public void filterMarkers(int type) {
        this.gender = type;
        onMapReady(map);
    }

    public interface OnMarkerClickListener {
        void onMarkerClicked(CompanionWithInfo info);
    }

    private void moveMap(GoogleMap map, Location location) {
        if(map != null && location != null) {
            map.clear();

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            map.setOnCameraChangeListener(cameraPosition -> zoom = cameraPosition.zoom);
            map.setOnMarkerClickListener(this);

            if(onMarkerClickListener != null) {
                paintUsers(map, latLng);
                paintCircle(map, latLng);
            }

            if(paintLocation)
                paintMyLocation(map, latLng);
        }
    }

    private void paintMyLocation(GoogleMap map, LatLng latLng) {
        LocationView.generateLocationView(getActivity(), preferencesManager.getUser(), locationView -> {
            if (locationView != null) {
                Marker marker = map.addMarker(new MarkerOptions()
                        .icon(createIcon(locationView))
                        .position(latLng));

                if(markers != null)
                    markers.put(marker, null);
            }
        });
    }

    private void paintCircle(GoogleMap map, LatLng latLng) {
        String stroke = "#30888888";
        String fill = "#90f4f4f4";

        map.addCircle(new CircleOptions()
                .center(latLng)
                .radius(5000)
                .strokeWidth(1)
                .strokeColor(Color.parseColor(stroke))
                .fillColor(Color.parseColor(fill)));

        for(int i = 4; i >= 1; i--) {
            map.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(i * 1000)
                    .strokeWidth(1)
                    .strokeColor(Color.parseColor(stroke))
                    .fillColor(Color.parseColor("#00000000")));
        }
    }

    private void paintUsers(GoogleMap map, LatLng myLocation) {
        if(markers != null && markersInfo != null) {
            markers.clear();

            List<CompanionWithInfo> filtered = filter(markersInfo);
            for(CompanionWithInfo info : filtered) {
                int icon = info.getClient().getGender() == 0 ? R.drawable.ic_boy_location : R.drawable.ic_girl_location;
                LatLng finish = new LatLng(info.getLatTarget(), info.getLngTarget());
                LatLng start = new LatLng(info.getLat(), info.getLng());

                LatLng point = calculatePoint(start, finish, myLocation);
                if(point != null) {
                    Marker marker = map.addMarker(new MarkerOptions()
                            .icon(createIcon(icon, 50, 115))
                            .position(point));

                    markers.put(marker, info);
                }
            }
        }
    }

    private List<CompanionWithInfo> filter(List<CompanionWithInfo> allInfo) {
        List<CompanionWithInfo> filtered = new ArrayList<>();

        if(gender == -1)
            return allInfo;

        for(CompanionWithInfo info : allInfo) {
            if(gender == info.getClient().getGender()) {
                filtered.add(info);
            }
        }

        return filtered;
    }

    private BitmapDescriptor createIcon(Bitmap bitmap) {
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor createIcon(@DrawableRes int res, int weight, int height) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(res);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, weight, height, false));
    }

    private LatLng calculatePoint(LatLng start, LatLng finish, LatLng myLocation) {
        double startDistance = calculateDistance(myLocation, start);
        double finishDistance = calculateDistance(myLocation, finish);

        if(startDistance <= 5) {
            return start;

        } else if(finishDistance <= 5) {
            return finish;

        } else return null;
    }

    private double calculateDistance(LatLng start, LatLng finish) {
        int earthRadius = 6371;

        double latDistance = Math.toRadians(finish.latitude - start.latitude);
        double lonDistance = Math.toRadians(finish.longitude - start.longitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(start.latitude)) * Math.cos(Math.toRadians(finish.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
