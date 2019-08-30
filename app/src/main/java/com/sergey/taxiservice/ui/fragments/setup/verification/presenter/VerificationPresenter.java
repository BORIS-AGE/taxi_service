package com.sergey.taxiservice.ui.fragments.setup.verification.presenter;

import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.setup.verification.view.VerificationView;
import com.sergey.taxiservice.util.RxTransformers;

import javax.inject.Inject;

public class VerificationPresenter extends BasePresenter<VerificationView> {

    private DataStore dataStore;
    private PreferencesManager manager;

    @Inject
    VerificationPresenter(DataStore dataStore, PreferencesManager manager) {
        this.dataStore = dataStore;
        this.manager = manager;
    }

    public void sendSmsVerification() {
        dataStore.sendSmsVerification()
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(status -> {
                    if(status.hasErrors() || !status.isSuccess() || !status.getResponse()) {
                        getView().onSendVerificationSmsFailed();
                    } else getView().onSendVerificationSmsSuccess();

                }, getView()::showError);
    }

    public void confirmSmsVerification(String code) {
        dataStore.confirmSmsVerification(code)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(status -> {
                    if(status.hasErrors() || !status.isSuccess() || !status.getResponse()) {
                        getView().onVerificationFailed();

                    } else {
                        UserModel userModel = manager.getUser();
                        userModel.setVerificated(1);

                        manager.saveUser(userModel);
                        getView().onVerificationSuccess();
                    }

                }, getView()::showError);
    }
}
