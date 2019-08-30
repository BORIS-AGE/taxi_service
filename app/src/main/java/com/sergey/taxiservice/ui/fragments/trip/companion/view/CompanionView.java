package com.sergey.taxiservice.ui.fragments.trip.companion.view;

import com.sergey.taxiservice.models.companion.CompanionWithInfo;
import com.sergey.taxiservice.ui.base.BaseView;

import java.util.List;

public interface CompanionView extends BaseView {

    void setListOfUsers(List<CompanionWithInfo> users);
}
