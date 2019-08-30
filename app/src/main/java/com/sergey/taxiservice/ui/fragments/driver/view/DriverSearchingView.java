package com.sergey.taxiservice.ui.fragments.driver.view;

import com.sergey.taxiservice.ui.base.BaseView;
import com.sergey.taxiservice.models.order.LoadOrder;

public interface DriverSearchingView extends BaseView {

    void driverFounded(LoadOrder loadOrder);

    void showSuccessMessage();

    void showFailureMessage();
}
