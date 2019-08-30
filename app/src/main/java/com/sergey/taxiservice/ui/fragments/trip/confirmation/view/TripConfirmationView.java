package com.sergey.taxiservice.ui.fragments.trip.confirmation.view;

import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseView;

import java.util.List;

public interface TripConfirmationView extends BaseView {

    void onCostChanged(String cost);

    void setUserData(String phone, String name);

    void onAddressFound(List<AddressModel> addressModels);
}
