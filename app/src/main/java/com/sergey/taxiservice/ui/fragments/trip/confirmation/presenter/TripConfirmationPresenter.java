package com.sergey.taxiservice.ui.fragments.trip.confirmation.presenter;

import com.google.gson.Gson;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.constants.CarType;
import com.sergey.taxiservice.constants.PayType;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.models.ride.Ride;
import com.sergey.taxiservice.routers.main.MainRouter;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationView;
import com.sergey.taxiservice.util.RxTransformers;
import com.sergey.taxiservice.util.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.sergey.taxiservice.ui.fragments.trip.confirmation.view.TripConfirmationFragment.RIDE_WITH_COMPANY;

public class TripConfirmationPresenter extends BasePresenter<TripConfirmationView> {

    private PreferencesManager preferencesManager;
    private DataStore dataStore;
    private MainRouter mainRouter;

    @Inject
    TripConfirmationPresenter(PreferencesManager preferencesManager, DataStore dataStore, MainRouter mainRouter) {
        this.preferencesManager = preferencesManager;
        this.dataStore = dataStore;
        this.mainRouter = mainRouter;
    }

    public void loadUserData() {
        UserModel userModel = preferencesManager.getUser();
        getView().setUserData(userModel.getPhoneNumber(), userModel.getFirstName());
    }

    public void searchCurrentAddress(double lat, double lon) {
        dataStore.searchAddress(lat, lon)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(getView()::onAddressFound, this::onErrorSearch);
    }

    private void onErrorSearch(Throwable throwable) {
        getView().showError(throwable);
        getView().onAddressFound(new ArrayList<>());
    }

    public void routeDataChanged(List<AddressModel> address) {
        if(address.size() < 2) {
            getView().onCostChanged("0");
            return;
        }

        dataStore.getCost(address).subscribe(cost -> {
            if(cost.isSuccess())
                getView().onCostChanged(cost.getCost());
        }, Throwable::printStackTrace);
    }

    public void createOrder(List<AddressModel> addresses, CarType carType, PayType payType, boolean[] states, Date date, int tripType, int person) {
        if(addresses.size() < 2) {
            getView().showMessage("Пожалуйста выберете начало и конец маршрута");
            return;
        }

        Map<String, Object> params = new HashMap<>();

        if(carType == CarType.PREMIUM) {
            params.put("premium", 1);
        }

        if(carType == CarType.MINIBUS) {
            params.put("minibus", 1);
        }

        if(carType == CarType.UNIVERSAL) {
            params.put("wagon", 1);
        }

        params.put("date", TimeUtils.convertToGeneralFormat(date));

        if(tripType == RIDE_WITH_COMPANY) {
            params.put("persons", person);

            dataStore.createCompanion(addresses, params).subscribe(createCompanion -> {
                if(createCompanion.isSuccess()) {

                    TaxiApplication.getDbInstance().executeTransaction(realm -> {
                        CurrentOrder currentOrder = new CurrentOrder();
                        currentOrder.setType(OrderType.COMPANION);
                        currentOrder.setState(OrderState.SEARCH_COMPANION);
                        currentOrder.setRideInfo(new Gson().toJson(createCompanion.getCompanion()));

                        realm.insertOrUpdate(currentOrder);
                        realm.close();
                    });

                    mainRouter.showCurrentRideScreen();
                }

            }, getView()::showError);

        } else {
            dataStore.createOrder(addresses, params).subscribe(createOrder -> {
                if(createOrder.isSuccess()) {

                    TaxiApplication.getDbInstance().executeTransaction(realm -> {
                        CurrentOrder currentOrder = new CurrentOrder();
                        currentOrder.setType(OrderType.SINGLE);
                        currentOrder.setState(OrderState.DRIVER_SEARCHING);

                        Ride ride = createOrder.getRide();
                        currentOrder.setRideInfo(new Gson().toJson(ride));

                        realm.insertOrUpdate(currentOrder);
                        realm.close();
                    });

                    mainRouter.showCurrentRideScreen();
                }

            }, getView()::showError);
        }
    }
}
