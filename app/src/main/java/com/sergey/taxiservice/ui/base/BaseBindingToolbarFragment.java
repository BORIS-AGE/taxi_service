package com.sergey.taxiservice.ui.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseBindingToolbarFragment<P extends BasePresenter, T extends ViewDataBinding> extends BaseBindingFragment<P, T> {

    public abstract String getTitle();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Activity activity = getActivity();
        if(activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if(actionBar != null) {
                actionBar.setTitle(getTitle());
            }
        }

        return view;
    }
}
