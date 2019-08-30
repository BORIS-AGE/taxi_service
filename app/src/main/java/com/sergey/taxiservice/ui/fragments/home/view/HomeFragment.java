package com.sergey.taxiservice.ui.fragments.home.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.databinding.FragmentHomeBinding;
import com.sergey.taxiservice.ui.fragments.home.presenter.HomePresenter;

public class HomeFragment extends BaseBindingToolbarFragment<HomePresenter, FragmentHomeBinding> implements HomeView {

    @Override
    public String getTitle() {
        return getString(R.string.home);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSoloRide.setOnClickListener(v -> presenter.onSingleRideClicked());
        binding.btnFindCompanion.setOnClickListener(v -> presenter.onRideWithCompanyClicked());
        presenter.loadCurrentOrder();
    }
}
