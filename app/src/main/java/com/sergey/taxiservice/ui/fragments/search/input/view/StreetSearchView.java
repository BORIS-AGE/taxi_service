package com.sergey.taxiservice.ui.fragments.search.input.view;

import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseView;

import java.util.List;

public interface StreetSearchView extends BaseView {

    void onSearchSuccess(List<AddressModel> results);
}
