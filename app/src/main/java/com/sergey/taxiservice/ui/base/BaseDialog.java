package com.sergey.taxiservice.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public abstract class BaseDialog extends DialogFragment {

    @LayoutRes
    public abstract int getLayoutResId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
//        setCancelable(false);

        ViewGroup.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes((LayoutParams) params);
    }

    public void showMessage(@StringRes int message) {
        showMessage(getString(message));
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Window getWindow() {
        return getDialog().getWindow();
    }
}
