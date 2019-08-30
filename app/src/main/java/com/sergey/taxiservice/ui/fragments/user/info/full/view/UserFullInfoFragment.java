package com.sergey.taxiservice.ui.fragments.user.info.full.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentUserFullInfoBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.user.info.full.presenter.UserFullInfoPresenter;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo.UserGeneralInfo;

import java.util.List;

import static com.sergey.taxiservice.ui.activities.ToolbarActivity.EXTRA_OPEN_WITH;
import static com.sergey.taxiservice.ui.activities.ToolbarActivity.OPEN_WITH_USER_INFO;

public class UserFullInfoFragment extends BaseBindingToolbarFragment<UserFullInfoPresenter, FragmentUserFullInfoBinding> implements UserFullInfoView {

    public static final String USER_ID = "user_id";

    public static void openInNewWindow(Activity activity, int userId) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        intent.putExtra(EXTRA_OPEN_WITH, OPEN_WITH_USER_INFO);
        intent.putExtra(USER_ID, userId);
        activity.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_user_account);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_full_info;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadUserInfo(getCurrentArguments().getInt(USER_ID));

        binding.winksTitle.setOnClickListener(v -> presenter.onWinksClicked(getCurrentArguments().getInt(USER_ID)));
        binding.respectTitle.setOnClickListener(v -> presenter.onRespectClicked(getCurrentArguments().getInt(USER_ID)));
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void setFeedbackInfo(List<Float> grades, List<FeedbackView.FeedbackParams> params, int count) {
        binding.feedback.setFeedbackGrades(grades, params);
        binding.feedbackTitle.setText(binding.feedbackTitle.getText() + " (" + count + ")");
    }

    @Override
    public void setUserGeneralInfo(UserGeneralInfo userInfo) {
        binding.generalInfo.setGeneralUserInfo(userInfo);
    }

    @Override
    public void disableShareFeatures() {
        binding.btnSendWinks.setVisibility(View.GONE);
        binding.btnSoloRespect.setVisibility(View.GONE);
        binding.btnWriteMessage.setVisibility(View.GONE);
    }

    @Override
    public void actionSuccessfulShared(String message) {
        showMessage(message);
    }
}
