package com.sergey.taxiservice.ui.fragments.user.info.full.view;

import com.sergey.taxiservice.ui.base.BaseView;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo.UserGeneralInfo;

import java.util.List;

public interface UserFullInfoView extends BaseView {

    void setFeedbackInfo(List<Float> grades, List<FeedbackView.FeedbackParams> params, int count);

    void setUserGeneralInfo(UserGeneralInfo userInfo);

    void disableShareFeatures();

    void actionSuccessfulShared(String message);
}
