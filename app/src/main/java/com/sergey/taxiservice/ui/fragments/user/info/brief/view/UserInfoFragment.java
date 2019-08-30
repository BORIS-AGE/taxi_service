package com.sergey.taxiservice.ui.fragments.user.info.brief.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentUserInfoBinding;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.ui.fragments.trip.invite.view.InvitePersonFragment;
import com.sergey.taxiservice.ui.fragments.user.info.brief.presenter.UserInfoPresenter;
import com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment;
import com.sergey.taxiservice.ui.views.FeedbackView;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo;

import java.util.List;

import static com.sergey.taxiservice.ui.fragments.user.info.full.view.UserFullInfoFragment.USER_ID;

public class UserInfoFragment extends BaseBindingFragment<UserInfoPresenter, FragmentUserInfoBinding> implements UserInfoView {

    public static final String MY_COMPANION_ID = "my_companion_id";
    public static final String USER_COMPANION_ID = "user_companion_id";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_info;
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadUserInfo(getCurrentArguments().getInt(USER_ID));

        binding.showProfile.setOnClickListener(v -> UserFullInfoFragment.openInNewWindow(getActivity(), getCurrentArguments().getInt(USER_ID)));
        binding.sendInvite.setOnClickListener(v -> presenter.sendInvite(
                getCurrentArguments().getInt(USER_COMPANION_ID),
                getCurrentArguments().getInt(MY_COMPANION_ID)));
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void setFeedbackInfo(List<Float> grades, List<FeedbackView.FeedbackParams> params) {
        binding.feedback.setFeedbackGrades(grades, params);
    }

    @Override
    public void setUserGeneralInfo(ViewUserGeneralInfo.UserGeneralInfo userInfo) {
        binding.generalInfo.setGeneralUserInfo(userInfo);
        binding.generalInfo.hideAbout();
    }

    @Override
    public void inviteSuccessfulSend(int inviteId) {
        InvitePersonFragment.openInNewWindow(getActivity(), inviteId, getCurrentArguments().getInt(USER_ID));
    }
}
