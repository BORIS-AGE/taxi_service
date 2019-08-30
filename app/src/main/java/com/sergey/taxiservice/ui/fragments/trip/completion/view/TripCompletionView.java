package com.sergey.taxiservice.ui.fragments.trip.completion.view;

import com.sergey.taxiservice.ui.base.BaseView;
import com.sergey.taxiservice.ui.views.FeedbackView;

import java.util.List;

public interface TripCompletionView extends BaseView {

    void onDriverInfoLoaded(String fullName, String genderWithAge);

    void onFeedbackParamsLoaded(List<FeedbackView.FeedbackParams> feedbackParams);
}
