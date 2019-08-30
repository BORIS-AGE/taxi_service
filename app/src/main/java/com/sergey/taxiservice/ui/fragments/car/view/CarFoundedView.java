package com.sergey.taxiservice.ui.fragments.car.view;

import com.sergey.taxiservice.ui.base.BaseView;

public interface CarFoundedView extends BaseView {

    void showSuccessMessage();

    void showFailureMessage();

    void isArchived();
}
