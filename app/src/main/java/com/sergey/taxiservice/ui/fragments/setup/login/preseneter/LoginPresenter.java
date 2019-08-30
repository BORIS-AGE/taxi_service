package com.sergey.taxiservice.ui.fragments.setup.login.preseneter;

import com.sergey.taxiservice.api.legacy.ApiResponse;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.routers.login.LoginRouter;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.setup.login.view.LoginView;
import com.sergey.taxiservice.util.RxTransformers;

import javax.inject.Inject;

import rx.Observable;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginRouter router;
    private DataStore dataStore;
    private PreferencesManager manager;

    @Inject
    LoginPresenter(LoginRouter router, DataStore dataStore, PreferencesManager manager) {
        this.router = router;
        this.manager = manager;
        this.dataStore = dataStore;
    }

    public boolean signIn() {
        String phoneNumber = getView().getPhoneNumber();
        String password = getView().getPassword();

        if(phoneNumber.isEmpty()) {
            getView().onPhoneNumberError();
            return false;
        }

        if(password.isEmpty()) {
            getView().onPasswordError();
            return false;
        }

        signIn(phoneNumber, password);
        return true;
    }

    public void onRegistrationClicked() {
        router.showRegistrationScreen(getView().getPhoneNumber(), getView().getPassword());
    }

    private void signIn(String phoneNumber, String password) {
        dataStore.signIn(phoneNumber, password)
                .flatMap(this::checkToken)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(this::onUserLoaded, getView()::showError);
    }

    private void onUserLoaded(UserModel userModel) {
        if(userModel.getVerificated() != 1)
            router.showVerificationScreen();
        else getView().onAuthSuccess();
    }

    private Observable<UserModel> checkToken(ApiResponse<String> apiResponse) {
        if(apiResponse.isSuccess()) {
            manager.saveToken(apiResponse.getResponse());
            return dataStore.getUserModel();

        } else return Observable.error(new Throwable("Пароль и номер телефона не совпадают"));
    }
}
