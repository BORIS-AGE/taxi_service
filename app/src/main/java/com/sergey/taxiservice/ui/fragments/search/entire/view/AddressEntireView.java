package com.sergey.taxiservice.ui.fragments.search.entire.view;

import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseView;

public interface AddressEntireView extends BaseView {

    void onAddressReady(AddressModel addressModel);
}
