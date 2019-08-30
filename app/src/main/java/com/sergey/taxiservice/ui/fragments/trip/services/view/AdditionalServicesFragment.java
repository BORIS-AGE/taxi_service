package com.sergey.taxiservice.ui.fragments.trip.services.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentAdditionalServicesBinding;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.trip.services.adapter.AdditionalServicesAdapter;
import com.sergey.taxiservice.ui.fragments.trip.services.presenter.AdditionalServicesPresenter;

public class AdditionalServicesFragment extends BaseBindingToolbarFragment<AdditionalServicesPresenter, FragmentAdditionalServicesBinding> implements AdditionalServicesView {

    public static void open(Fragment activity) {
        Intent intent = new Intent(activity.getActivity(), ToolbarActivity.class);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_ADDITIONAL_SERVICES);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public String getTitle() {
        return getString(R.string.toolbar_title_additional_services);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_additional_services;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] additionalServices = getResources().getStringArray(R.array.view_array_additional_services);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        AdditionalServicesAdapter adapter = new AdditionalServicesAdapter(additionalServices);

        binding.additionalServices.setLayoutManager(layoutManager);
        binding.additionalServices.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.additionalServices.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        binding.additionalServices.addItemDecoration(dividerItemDecoration);

        binding.btnSave.setOnClickListener(v -> {
            Intent intent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, intent.putExtra(AdditionalServicesFragment.class.getSimpleName(), adapter.getServicesStates()));
            getActivity().finish();
        });
    }
}
