package com.sergey.taxiservice.ui.fragments.trip.invite.view;

import com.sergey.taxiservice.ui.base.BaseView;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo;

import java.util.List;

public interface InvitePersonView extends BaseView {

    void setFeedbackInfo(List<Float> grades, List<FeedbackView.FeedbackParams> params);

    void setUserGeneralInfo(ViewUserGeneralInfo.UserGeneralInfo userInfo);
}
