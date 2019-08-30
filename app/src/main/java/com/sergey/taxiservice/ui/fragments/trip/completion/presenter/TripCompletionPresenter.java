package com.sergey.taxiservice.ui.fragments.trip.completion.presenter;

import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.manager.resources.ResourcesManager;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.trip.completion.view.TripCompletionView;

import javax.inject.Inject;

public class TripCompletionPresenter extends BasePresenter<TripCompletionView> {

    private ResourcesManager resourcesManager;

    @Inject
    TripCompletionPresenter(ResourcesManager resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public void loadDriverInfo() {

    }

    public void loadResources() {
        getView().onFeedbackParamsLoaded(resourcesManager.getFeedbackResources());
    }

    public void onPunctualitySelected(float grade) {

    }

    public void onSociabilityScelected(float grade) {

    }

    public void onRecommendedSelected(float grade) {

    }

    public void onNotRecommendedSelected(float grade) {

    }

    public void onTripCompletionClicked() {
        TaxiApplication.getDbInstance().executeTransaction(realm -> realm.delete(CurrentOrder.class));
    }
}
