package com.sergey.taxiservice.ui.fragments.trip.services.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.ItemAdditionalServicesBinding;

public class AdditionalServicesAdapter extends RecyclerView.Adapter<AdditionalServiceViewHolder> {

    private String[] additionalServices;
    private boolean[] servicesStates;

    public AdditionalServicesAdapter(String[] additionalServices) {
        this.additionalServices = additionalServices;
        this.servicesStates = new boolean[additionalServices.length];
    }

    @NonNull
    @Override
    public AdditionalServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        ItemAdditionalServicesBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_additional_services, parent, false);
        return new AdditionalServiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionalServiceViewHolder holder, int position) {
        ItemAdditionalServicesBinding binding = holder.getBinding();
        binding.serviceName.setText(additionalServices[position]);

        binding.serviceState.setBackgroundDrawable(getImage(binding.getRoot().getContext(), R.drawable.ic_rectangle));
        binding.serviceState.setChecked(servicesStates[position]);
        binding.serviceState.setOnCheckedChangeListener((compoundButton, b) -> servicesStates[position] = b);
    }

    @Override
    public int getItemCount() {
        return additionalServices.length;
    }

    private Drawable getImage(Context context, @DrawableRes int drawableId) {
        return context.getResources().getDrawable(drawableId);
    }

    public boolean[] getServicesStates() {
        return servicesStates;
    }
}
