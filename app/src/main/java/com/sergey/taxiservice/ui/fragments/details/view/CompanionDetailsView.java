package com.sergey.taxiservice.ui.fragments.details.view;

import com.sergey.taxiservice.models.companion.RideGeneralInfo;
import com.sergey.taxiservice.models.order.CreateOrder;
import com.sergey.taxiservice.ui.base.BaseView;

public interface CompanionDetailsView extends BaseView {

    void showDriverSearchingScreen(CreateOrder order);

    void setInfo(RideGeneralInfo info);

    void showError(String message);

    void reloadData();
}
