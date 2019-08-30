package com.sergey.taxiservice.ui.fragments.trip.execution.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentTripExecutionBinding;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.ui.fragments.trip.execution.presenter.TripExecutionPresenter;

public class TripExecutionFragment extends BaseBindingFragment<TripExecutionPresenter, FragmentTripExecutionBinding> implements TripExecutionView {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_trip_execution;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
