package com.sergey.taxiservice.ui.fragments.trip.services.adapter;

import android.support.v7.widget.RecyclerView;

import com.sergey.taxiservice.databinding.ItemAdditionalServicesBinding;

public class AdditionalServiceViewHolder extends RecyclerView.ViewHolder {

    private ItemAdditionalServicesBinding binding;

    public AdditionalServiceViewHolder(ItemAdditionalServicesBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemAdditionalServicesBinding getBinding() {
        return binding;
    }
}
