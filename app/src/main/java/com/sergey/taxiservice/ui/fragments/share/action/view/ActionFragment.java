package com.sergey.taxiservice.ui.fragments.share.action.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentActionBinding;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.share.action.presenter.ActionPresenter;
import com.sergey.taxiservice.ui.views.ViewUserGeneralInfo;

public class ActionFragment extends BaseBindingToolbarFragment<ActionPresenter, FragmentActionBinding> implements ActionView {

    public static final String USER_ID = "user_id";
    public static final String ACTION_TYPE = "action_type";
    public static final int RESPECT = 0;
    public static final int WINKS = 1;

    @Override
    public String getTitle() {
        return getCurrentArguments().getInt(ACTION_TYPE) == RESPECT ?
                getString(R.string.toolbar_title_respect) : getString(R.string.toolbar_title_winks);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_action;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadUser(getCurrentArguments().getInt(USER_ID));

        binding.winksTitle.setOnClickListener(v -> presenter.onWinksClicked(getCurrentArguments().getInt(USER_ID)));
        binding.respectTitle.setOnClickListener(v -> presenter.onRespectClicked(getCurrentArguments().getInt(USER_ID)));
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onUserLoaded(ViewUserGeneralInfo.UserGeneralInfo info, String userName) {
        binding.generalInfo.setGeneralUserInfo(info);

        if(getCurrentArguments().getInt(ACTION_TYPE) == WINKS) {
            binding.btnSendWinks.setVisibility(View.VISIBLE);
            if(info.getGender() == 0) {
                binding.title.setText("Ваш попутчик " + userName + " выразил Вам симпатию");
            } else {
                binding.title.setText("Ваша попутчица " + userName + " выразила Вам симпатию");
            }

        } else {
            binding.btnSoloRespect.setVisibility(View.VISIBLE);
            if(info.getGender() == 0) {
                binding.title.setText("Ваш попутчик " + userName + " выразил Вам уважение и поблагодарил за поездку");
            } else {
                binding.title.setText("Ваша попутчица " + userName + " выразила Вам уважение и поблагодарила за поездку");
            }
        }
    }

    @Override
    public void actionSuccessfulShared(String message) {
        showMessage(message);
    }
}
