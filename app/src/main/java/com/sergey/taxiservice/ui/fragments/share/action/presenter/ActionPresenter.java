package com.sergey.taxiservice.ui.fragments.share.action.presenter;

import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.share.action.view.ActionView;
import com.sergey.taxiservice.util.RxTransformers;

import javax.inject.Inject;

public class ActionPresenter extends BasePresenter<ActionView> {

    private DataStore dataStore;

    @Inject
    ActionPresenter(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void loadUser(int userId) {
        dataStore.getUserModel(userId)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(userModel -> getView().onUserLoaded(userModel.getGeneralInfo(), userModel.getFirstName()), getView()::showError);
    }

    public void onWinksClicked(int userId) {
        dataStore.sendWinks(userId)
                .subscribe(actionModel -> getView().actionSuccessfulShared("Вы успешно подмигнули"), getView()::showError);
    }

    public void onRespectClicked(int userId) {
        dataStore.sendRespect(userId)
                .subscribe(actionModel -> getView().actionSuccessfulShared("Вы выразили уважение"), getView()::showError);
    }
}
