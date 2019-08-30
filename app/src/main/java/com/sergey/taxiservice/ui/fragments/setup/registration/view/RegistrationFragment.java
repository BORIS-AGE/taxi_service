package com.sergey.taxiservice.ui.fragments.setup.registration.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.databinding.FragmentRegistrationBinding;
import com.sergey.taxiservice.ui.fragments.setup.registration.presenter.RegistrationPresenter;
import com.sergey.taxiservice.util.UiUtils;


import org.json.JSONObject;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RegistrationFragment extends BaseBindingFragment<RegistrationPresenter, FragmentRegistrationBinding> implements RegistrationView, OnEditorActionListener {

    public static final String EXTRA_PHONE_NUMBER = "extra_phone_number";
    public static final String EXTRA_PASSWORD = "extra_password";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_registration;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.etPhoneNumber.setText(getCurrentArguments().getString(EXTRA_PHONE_NUMBER));
        binding.etPassword.setText(getCurrentArguments().getString(EXTRA_PASSWORD));

        binding.etLastName.setOnEditorActionListener(this);
        binding.btnRegister.setOnClickListener(v -> {
            hideInputMethod();
            presenter.signUp();
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE)
            return !presenter.signUp();
        return false;
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
    public String getConfirmPassword() {
        return binding.etPasswordConfirm.getText().toString();
    }

    @Override
    public String getEmail() {
        return binding.etEmail.getText().toString();
    }

    @Override
    public String getFirstName() {
        return binding.etFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return binding.etLastName.getText().toString();
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
    public void onConfirmPasswordError() {
        binding.etPasswordConfirm.setError(getString(R.string.error_password_confirm));
    }

    @Override
    public void onEmailError() {
        binding.etEmail.setError(getString(R.string.field_error_cannot_be_empty));
    }

    @Override
    public void onFirstNameError() {
        binding.etFirstName.setError(getString(R.string.field_error_cannot_be_empty));
    }

    @Override
    public void onLastNameError() {
        binding.etLastName.setError(getString(R.string.field_error_cannot_be_empty));
    }

    @Override
    public void onRegistrationFailed(JSONObject errors) {
        String unknownError = getString(R.string.error_unknown_short);
        UiUtils.setErrorsIfExist(errors, "phone_number", binding.etPhoneNumber, unknownError);
        UiUtils.setErrorsIfExist(errors, "email", binding.etEmail, unknownError);
        UiUtils.setErrorsIfExist(errors, "password", binding.etPassword, unknownError);
        UiUtils.setErrorsIfExist(errors, "password_confirmation", binding.etPasswordConfirm, unknownError);
        UiUtils.setErrorsIfExist(errors, "name", binding.etFirstName, unknownError);
        UiUtils.setErrorsIfExist(errors, "surname", binding.etLastName, unknownError);
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
