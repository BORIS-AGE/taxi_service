package com.sergey.taxiservice.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseBindingFragment<P extends BasePresenter, T extends ViewDataBinding> extends Fragment implements BaseView {

    @Inject
    protected P presenter;
    protected T binding;

    private ProgressDialog progressDialog;

    @LayoutRes
    public abstract int getLayoutResId();

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    @SuppressWarnings("All")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        presenter.onCreateView(this);
        return binding.getRoot();
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Загрузка...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        if(getActivity() != null) {
            ((BaseActivity) getActivity()).showMessage(message);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        showMessage(throwable.getMessage());
    }

    public void addToArguments(String key, int value) {
        Bundle bundle = getCurrentArguments();
        bundle.putInt(key, value);
        setArguments(bundle);
    }

    public void addToArguments(String key, String value) {
        Bundle bundle = getCurrentArguments();
        bundle.putString(key, value);
        setArguments(bundle);
    }

    public void addToArguments(String key, Parcelable value) {
        Bundle bundle = getCurrentArguments();
        bundle.putParcelable(key, value);
        setArguments(bundle);
    }

    public Bundle getCurrentArguments() {
        Bundle arguments = getArguments();
        if(arguments == null)
            arguments = new Bundle();

        return  arguments;
    }
}
