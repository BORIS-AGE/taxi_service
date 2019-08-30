package com.sergey.taxiservice.ui.fragments.driver.presenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.ui.fragments.driver.view.DriverSearchingView;

import javax.inject.Inject;

public class DriverSearchingPresenter extends BasePresenter<DriverSearchingView> {

    private static final int TIME = 10 * 1000;

    private DataStore dataStore;

    private CheckingTask checkingTask;
    private Handler repeatedHandler;

    @Inject
    DriverSearchingPresenter(DataStore dataStore) {
        this.repeatedHandler = new Handler();
        this.dataStore = dataStore;
    }

    public void startCheckingDriver(int orderId, int orderType) {
        checkingTask = new CheckingTask(orderId, orderType);
        repeatedHandler.postDelayed(checkingTask, TIME);
    }

    public void stopCheckingDriver() {
        repeatedHandler.removeCallbacks(checkingTask);
    }

    public void cancelOrder(int orderId, int orderType) {
        stopCheckingDriver();

        if(orderType == OrderType.SINGLE) {
            dataStore.cancelOrder(orderId).subscribe(baseModel -> {

                if(baseModel.isSuccess()) {
                    TaxiApplication.getDbInstance().executeTransaction(realm -> realm.delete(CurrentOrder.class));
                    getView().showSuccessMessage();

                } else {
                    getView().showFailureMessage();
                    startCheckingDriver(orderId, orderType);
                }

            }, Throwable::printStackTrace);

        } else {
            dataStore.cancelCompanion(orderId).subscribe(baseModel2 -> {

                if(baseModel2.isSuccess()) {
                    TaxiApplication.getDbInstance().executeTransaction(realm -> realm.delete(CurrentOrder.class));
                    getView().showSuccessMessage();

                } else {
                    getView().showFailureMessage();
                    startCheckingDriver(orderId, orderType);
                }

            }, Throwable::printStackTrace);
        }
    }

    private class CheckingTask implements Runnable {

        private int orderId;
        private int orderType;

        private CheckingTask(int orderId, int orderType) {
            this.orderId = orderId;
            this.orderType = orderType;
        }

        @Override
        public void run() {
            if(orderType == OrderType.SINGLE) {
                dataStore.getOrder(orderId).subscribe(loadOrder -> {

                    if(loadOrder.getEvos().getOrderCarInfo() == null)
                        repeatedHandler.postDelayed(checkingTask, TIME);
                    else {
                        TaxiApplication.getDbInstance().executeTransaction(realm -> {
                            CurrentOrder currentOrder = new CurrentOrder();
                            currentOrder.setType(OrderType.SINGLE);
                            currentOrder.setState(OrderState.CAR_FOUND);
                            currentOrder.setRideInfo(new Gson().toJson(loadOrder));

                            realm.insertOrUpdate(currentOrder);
                        });

                        getView().driverFounded(loadOrder);
                    }

                }, Throwable::printStackTrace);

            } else {
                dataStore.getCompanionOrder(orderId).subscribe(loadOrder -> {

                    if(loadOrder.getEvos().getOrderCarInfo() == null)
                        repeatedHandler.postDelayed(checkingTask, TIME);
                    else {
                        TaxiApplication.getDbInstance().executeTransaction(realm -> {
                            CurrentOrder currentOrder = new CurrentOrder();
                            currentOrder.setType(OrderType.COMPANION);
                            currentOrder.setState(OrderState.CAR_FOUND);
                            currentOrder.setRideInfo(new Gson().toJson(loadOrder));

                            realm.insertOrUpdate(currentOrder);
                        });

                        getView().driverFounded(loadOrder);
                    }

                }, Throwable::printStackTrace);
            }
        }
    }
}
