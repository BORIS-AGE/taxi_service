package com.sergey.taxiservice.ui.fragments.setup.registration.presenter;

import com.sergey.taxiservice.api.legacy.ApiResponse;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.routers.login.LoginRouter;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.setup.registration.view.RegistrationView;
import com.sergey.taxiservice.util.RxTransformers;

import javax.inject.Inject;

import rx.Observable;

public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private DataStore dataStore;
    private LoginRouter loginRouter;
    private PreferencesManager manager;

    @Inject
    RegistrationPresenter(DataStore dataStore, LoginRouter loginRouter, PreferencesManager manager) {
        this.manager = manager;
        this.dataStore = dataStore;
        this.loginRouter = loginRouter;
    }

    public boolean signUp() {
        if(getView().getPhoneNumber().isEmpty()) {
            getView().onPhoneNumberError();
            return false;
        }

        if(getView().getPassword().isEmpty()) {
            getView().onPasswordError();
            return false;
        }

        if(getView().getConfirmPassword().isEmpty()) {
            getView().onConfirmPasswordError();
            return false;
        }

        if(!getView().getPassword().equals(getView().getConfirmPassword())) {
            getView().onConfirmPasswordError();
            return false;
        }

        if(getView().getEmail().isEmpty()) {
            getView().onEmailError();
            return false;
        }

        if(getView().getFirstName().isEmpty()) {
            getView().onFirstNameError();
            return false;
        }

        if(getView().getLastName().isEmpty()) {
            getView().onLastNameError();
            return false;
        }

        signUp(
                getView().getPhoneNumber(),
                getView().getPassword(),
                getView().getConfirmPassword(),
                getView().getEmail(),
                getView().getFirstName(),
                getView().getLastName()
        );
        return true;
    }

    private void signUp(String phoneNumber, String password, String confirmPassword, String email, String firstName, String lastName){
        dataStore.signUp(phoneNumber, email, password, confirmPassword, firstName, lastName)
                .flatMap(this::checkToken)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(this::onUserLoaded, getView()::showError);
    }

    private void onUserLoaded(UserModel userModel) {
        if(userModel.getVerificated() != 1)
            loginRouter.showVerificationScreen();
    }

    private Observable<UserModel> checkToken(ApiResponse<String> apiResponse) {
        if(apiResponse.hasErrors()) {
            getView().onRegistrationFailed(apiResponse.getErrors());
        }

        if(apiResponse.isSuccess()) {
            manager.saveToken(apiResponse.getResponse());
            return dataStore.getUserModel();

        }

        return Observable.error(new Throwable());
    }
}
