package com.sergey.taxiservice.ui.fragments.user.info.brief.presenter;

import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.resources.ResourcesManager;
import com.sergey.taxiservice.models.UserModel;
import com.sergey.taxiservice.models.share.FeedbackModel;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.user.info.brief.view.UserInfoView;
import com.sergey.taxiservice.util.RxTransformers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class UserInfoPresenter extends BasePresenter<UserInfoView> {

    private DataStore dataStore;
    private ResourcesManager resourcesManager;

    @Inject
    UserInfoPresenter(DataStore dataStore, ResourcesManager resourcesManager) {
        this.dataStore = dataStore;
        this.resourcesManager = resourcesManager;
    }

    public void loadUserInfo(int userId) {
        Observable.zip(dataStore.getUserModel(userId), dataStore.loadFeedback(userId).map(this::calculateMiddleValues), Container::new)
                .compose(RxTransformers.applyOnBeforeAndAfter(getView()::showProgress, getView()::hideProgress))
                .subscribe(this::showUserInfo, getView()::showError);
    }

    public void sendInvite(int host, int client) {
        dataStore.sendInvite(host, client).subscribe(baseModel -> {
            if(baseModel.isSuccess()) {
                getView().showMessage("Вы присоединились к поездке.");
                getView().inviteSuccessfulSend(baseModel.getId());

            } else {
                getView().showMessage("Вы уже участвуете в этой поездке.");
            }

        }, Throwable::printStackTrace);
    }

    private void showUserInfo(Container container) {
        getView().setUserGeneralInfo(container.userModel.getGeneralInfo());
        getView().setFeedbackInfo(container.grades, resourcesManager.getFeedbackResources());
    }

    private List<Float> calculateMiddleValues(List<FeedbackModel> feedbackModels) {
        List<Float> grades = new ArrayList<>();
        float punctuality = 0;
        float sociability = 0;
        float recommend = 0;
        float not_recommend = 0;

        for (FeedbackModel feedbackModel : feedbackModels) {
            punctuality += feedbackModel.getPunctuality();
            sociability += feedbackModel.getSociability();
            recommend += feedbackModel.getRecommend();
            not_recommend += feedbackModel.getNot_recommend();
        }

        grades.add(punctuality == 0 ? 1 : punctuality / feedbackModels.size());
        grades.add(sociability == 0 ? 1 : sociability  / feedbackModels.size());
        grades.add(recommend == 0 ? 1 : recommend  / feedbackModels.size());
        grades.add(not_recommend == 0 ? 1 : not_recommend  / feedbackModels.size());
        return grades;
    }

    private class Container {

        private UserModel userModel;
        private List<Float> grades;

        Container(UserModel userModel, List<Float> grades) {
            this.userModel = userModel;
            this.grades = grades;
        }
    }
}
