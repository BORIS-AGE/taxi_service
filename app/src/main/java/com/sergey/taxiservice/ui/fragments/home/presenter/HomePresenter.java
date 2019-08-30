package com.sergey.taxiservice.ui.fragments.home.presenter;

import com.google.gson.Gson;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.routers.main.MainRouter;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.models.order.LoadOrder;
import com.sergey.taxiservice.models.ride.Ride;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.ui.fragments.home.view.HomeView;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeView> {

    private DataStore dataStore;
    private MainRouter mainRouter;

    @Inject
    HomePresenter(DataStore dataStore, MainRouter mainRouter) {
        this.dataStore = dataStore;
        this.mainRouter = mainRouter;
    }

    public void onSingleRideClicked() {
        mainRouter.showSingleTripConfirmationScreen();
    }

    public void onRideWithCompanyClicked() {
        mainRouter.showTripWithCompanyConfirmationScreen();
    }

    public void loadCurrentOrder() {
        CurrentOrder currentOrder = TaxiApplication.getDbInstance().where(CurrentOrder.class).findFirst();
        if(currentOrder != null && currentOrder.getState() != OrderState.SEARCH_COMPANION) {

            int orderId = currentOrder.getState() == OrderState.CAR_FOUND ?
                    new Gson().fromJson(currentOrder.getRideInfo(), LoadOrder.class).getRide().getId() :
                    new Gson().fromJson(currentOrder.getRideInfo(), Ride.class).getId();

            if(currentOrder.getType() == OrderType.SINGLE) {
                dataStore.getOrder(orderId).subscribe(loadOrder -> {
                    if(loadOrder.getEvos().isArrchived()) {
//                        TaxiApplication.getDbInstance().executeTransaction(realm -> realm.delete(CurrentOrder.class));
                        TaxiApplication.getDbInstance().executeTransaction(realm -> {
                            CurrentOrder current = new CurrentOrder();
                            current.setType(OrderType.SINGLE);
                            current.setState(OrderState.CAR_FOUND);
                            current.setRideInfo(new Gson().toJson(loadOrder));

                            realm.insertOrUpdate(current);
                        });

                    }  else if(loadOrder.getEvos().getOrderCarInfo() != null) {

                        TaxiApplication.getDbInstance().executeTransaction(realm -> {
                            CurrentOrder current = new CurrentOrder();
                            current.setType(OrderType.SINGLE);
                            current.setState(OrderState.CAR_FOUND);
                            current.setRideInfo(new Gson().toJson(loadOrder));

                            realm.insertOrUpdate(current);
                        });
                    }

                }, Throwable::printStackTrace);

            }

//            else {
//                dataStore.getCompanionOrder(orderId).subscribe(loadOrder -> {
//                    if(loadOrder.getEvos().isArrchived()) {
//                        TaxiApplication.getDbInstance().executeTransaction(realm -> realm.delete(CurrentOrder.class));
//
//                    } else if(loadOrder.getEvos().getOrderCarInfo() != null) {
//                        TaxiApplication.getDbInstance().executeTransaction(realm -> {
//                            CurrentOrder current = new CurrentOrder();
//                            current.setType(OrderType.COMPANION);
//                            current.setState(OrderState.CAR_FOUND);
//                            current.setRideInfo(new Gson().toJson(loadOrder));
//
//                            realm.insertOrUpdate(current);
//                        });
//                    }
//
//                }, Throwable::printStackTrace);
//            }
        }
    }
}
