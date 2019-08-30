package com.sergey.taxiservice.ui.fragments.trip.companion.presenter;

import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.trip.companion.view.CompanionView;

import javax.inject.Inject;

public class CompanionPresenter extends BasePresenter<CompanionView> {

    private DataStore dataStore;

    @Inject
    CompanionPresenter(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void loadCompanions(int id) {
        dataStore.getCompanion(id).subscribe(loadCompanion -> {
            if(loadCompanion.isSuccess()) {
                getView().setListOfUsers(loadCompanion.getUsers());
            }
        }, Throwable::printStackTrace);
    }
}
