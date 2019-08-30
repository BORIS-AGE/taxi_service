package com.sergey.taxiservice.ui.fragments.share.action.view;

import com.sergey.taxiservice.ui.base.BaseView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo;

public interface ActionView extends BaseView {

    void onUserLoaded(ViewUserGeneralInfo.UserGeneralInfo info, String userName);

    void actionSuccessfulShared(String message);
}
