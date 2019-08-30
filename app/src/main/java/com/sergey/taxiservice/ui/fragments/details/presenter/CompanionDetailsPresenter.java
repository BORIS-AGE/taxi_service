package com.sergey.taxiservice.ui.fragments.details.presenter;

import com.google.gson.Gson;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.details.view.CompanionDetailsView;

import javax.inject.Inject;

public class CompanionDetailsPresenter extends BasePresenter<CompanionDetailsView> {

    private final static int YES = 1;
    private final static int NO = 2;

    private DataStore dataStore;

    @Inject
    CompanionDetailsPresenter(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void loadCompanionInfo(int id) {
        dataStore.loadCompanionInfo(id).subscribe(rideInfo -> {
            if(rideInfo.isSuccess() && rideInfo.getRideGeneralInfo() != null) {
                getView().setInfo(rideInfo.getRideGeneralInfo());
            } else getView().showError("Невозможно загрузить данные про поездку.");

        }, Throwable::printStackTrace);
    }

    public void acceptRide(int id, int clientId) {
        dataStore.updateInvite(id, clientId, YES).subscribe(model -> {
            if(model.isSuccess()) {
                getView().showError("Статус пользователя изменен.");
                getView().reloadData();

            } else {
                getView().showError("Не удалось изменить статус пользователя.");
            }

        }, Throwable::printStackTrace);
    }

    public void deleteRide(int id, int clientId) {
        dataStore.updateInvite(id, clientId, NO).subscribe(model -> {
            if(model.isSuccess()) {
                getView().showError("Статус пользователя изменен.");
                getView().reloadData();

            } else {
                getView().showError("Не удалось изменить статус пользователя.");
            }

        }, Throwable::printStackTrace);
    }

    public void findCar(int id) {
        dataStore.startCompanion(id).subscribe(createOrder -> {
            if(createOrder.isSuccess()) {

                TaxiApplication.getDbInstance().executeTransaction(realm -> {
                    CurrentOrder currentOrder = new CurrentOrder();
                    currentOrder.setType(OrderType.COMPANION);
                    currentOrder.setState(OrderState.DRIVER_SEARCHING);
                    currentOrder.setRideInfo(new Gson().toJson(createOrder.getRide()));

                    realm.insertOrUpdate(currentOrder);
                });

                getView().showDriverSearchingScreen(createOrder);
            }

        }, Throwable::printStackTrace);
    }
}
