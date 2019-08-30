package com.sergey.taxiservice.ui.fragments.user.settings.presenter;

import android.net.Uri;

import com.sergey.taxiservice.api.legacy.ApiResponse;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.user.settings.view.SettingsView;
import com.sergey.taxiservice.util.ImageLoader;
import com.sergey.taxiservice.util.RxTransformers;

import javax.inject.Inject;

import rx.Observable;

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private Uri avaUri;
    private UserModel userModel;
    private DataStore dataStore;
    private PreferencesManager preferencesManager;

    @Inject
    SettingsPresenter(PreferencesManager preferencesManager, DataStore dataStore) {
        this.preferencesManager = preferencesManager;
        this.dataStore = dataStore;
    }

    public void loadUser() {
        userModel = preferencesManager.getUser();
        getView().setAvatar(userModel.getAvatar());
        getView().setPhoneNumber(userModel.getPhoneNumber());
        getView().setPassword(userModel.getPassword());
        getView().setFirstName(userModel.getFirstName());
        getView().setLastName(userModel.getLastName());
        getView().setPatronymic(userModel.getPatronymic());
        getView().setGender(userModel.getGender());
        getView().setAge(userModel.getAge());
        getView().setAbout(userModel.getAbout());
    }

    public void saveAvatar(Uri uri) {
        this.avaUri = uri;
    }

    public void deleteAvatar() {
        dataStore.deleteAvatar()
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(success -> {
                    if(success) {
                        getView().onAvaDeleted();
                    }
                }, getView()::showError);
    }

    public void saveChanges() {
        if(getView().getPhoneNumber().isEmpty()) {
            getView().onPhoneNumberError();
            return;
        }

        if(getView().getPassword().isEmpty()) {
            getView().onPasswordError();
            return;
        }

        if(getView().getFirstName().isEmpty()) {
            getView().onFirstNameError();
            return;
        }

        if(getView().getLastName().isEmpty()) {
            getView().onLastNameError();
            return;
        }

        userModel.setPhoneNumber(getView().getPhoneNumber());
        userModel.setPassword(getView().getPassword());
        userModel.setFirstName(getView().getFirstName());
        userModel.setLastName(getView().getLastName());
        userModel.setPatronymic(getView().getPatronymic());
        userModel.setGender(getView().getGender());
        userModel.setAge(getView().getAge());
        userModel.setAbout(getView().getAbout());

        dataStore.editUserModel(userModel, avaUri)
                .flatMap(this::checkUser)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(this::onUserEdited, getView()::showError);
    }

    private void onUserEdited(UserModel userModel) {
        preferencesManager.saveUser(userModel);
        ImageLoader.invalidateUrl(userModel.getAvatar());
        getView().onEditSuccess();
    }

    private Observable<UserModel> checkUser(ApiResponse<Boolean> apiResponse) {
        if(apiResponse.hasErrors()) {
            getView().onEditFailed(apiResponse.getErrors());
        }

        if(apiResponse.isSuccess() && apiResponse.getResponse()) {
            return dataStore.getUserModel();
        }

        return Observable.error(new Throwable());
    }
}
