package com.sergey.taxiservice.manager.resources;

import com.sergey.taxiservice.ui.views.FeedbackView;

import java.util.List;

public interface ResourcesManager {

    List<FeedbackView.FeedbackParams> getFeedbackResources();
}
