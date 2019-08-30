package com.sergey.taxiservice.ui.fragments.trip.invite.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentInvitePersonBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.trip.invite.presenter.InvitePersonPresenter;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo;

import java.util.List;

import static com.sergey.taxiservice.ui.activities.ToolbarActivity.EXTRA_OPEN_WITH;
import static com.sergey.taxiservice.ui.activities.ToolbarActivity.OPEN_WITH_INVITE_PERSON;

public class InvitePersonFragment extends BaseBindingToolbarFragment<InvitePersonPresenter, FragmentInvitePersonBinding> implements InvitePersonView {

    public static final String USER_ID = "user_id";
    public static final String INVITE_ID = "invite_id";

    public static void openInNewWindow(Activity activity, int inviteId, int userId) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        intent.putExtra(EXTRA_OPEN_WITH, OPEN_WITH_INVITE_PERSON);
        intent.putExtra(USER_ID, userId);
        intent.putExtra(INVITE_ID, inviteId);
        activity.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_invite_person);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_invite_person;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadUserInfo(getCurrentArguments().getInt(USER_ID));
    }

    @Override
    public void setFeedbackInfo(List<Float> grades, List<FeedbackView.FeedbackParams> params) {
        binding.feedback.setFeedbackGrades(grades, params);
    }

    @Override
    public void setUserGeneralInfo(ViewUserGeneralInfo.UserGeneralInfo userInfo) {
        binding.generalInfo.setGeneralUserInfo(userInfo);
        binding.generalInfo.hideAbout();
    }
}
