package com.sergey.taxiservice.ui.fragments.share.approve.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentApproveBinding;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.share.approve.presenter.ApprovePresenter;

public class ApproveFragment extends BaseBindingToolbarFragment<ApprovePresenter, FragmentApproveBinding> implements ApproveView {

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_notification);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_approve;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
