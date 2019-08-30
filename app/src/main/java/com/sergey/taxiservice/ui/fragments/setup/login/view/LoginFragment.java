package com.sergey.taxiservice.ui.fragments.setup.login.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.databinding.FragmentLoginBinding;
import com.sergey.taxiservice.ui.activities.MainActivity;
import com.sergey.taxiservice.ui.fragments.setup.login.preseneter.LoginPresenter;


import static android.content.Context.INPUT_METHOD_SERVICE;

public class LoginFragment extends BaseBindingFragment<LoginPresenter, FragmentLoginBinding> implements LoginView, OnClickListener, OnEditorActionListener {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.etPassword.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE)
            return !presenter.signIn();
        return false;
    }

    @Override
    public void onClick(View view) {
        hideInputMethod();

        switch (view.getId()) {
            case R.id.btn_sign_in:
                presenter.signIn();
                break;

            case R.id.btn_sign_up:
                presenter.onRegistrationClicked();
                break;
        }
    }

    @Override
    public String getPhoneNumber() {
        return binding.etPhoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return binding.etPassword.getText().toString();
    }

    @Override
    public void onPhoneNumberError() {
        binding.etPhoneNumber.setError(getString(R.string.field_error_cannot_be_empty));
    }

    @Override
    public void onPasswordError() {
        binding.etPassword.setError(getString(R.string.field_error_cannot_be_empty));
    }

    @Override
    public void onAuthSuccess() {
        MainActivity.open(getActivity(), true);
    }

    private void hideInputMethod() {
        Activity activity = getActivity();

        if(activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();

            if(inputMethodManager != null && view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
