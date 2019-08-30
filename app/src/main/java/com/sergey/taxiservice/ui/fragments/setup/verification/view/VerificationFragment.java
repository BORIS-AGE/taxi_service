package com.sergey.taxiservice.ui.fragments.setup.verification.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.ui.base.BaseBindingFragment;
import com.sergey.taxiservice.databinding.FragmentVerificationBinding;
import com.sergey.taxiservice.ui.activities.MainActivity;
import com.sergey.taxiservice.ui.fragments.setup.verification.presenter.VerificationPresenter;

public class VerificationFragment extends BaseBindingFragment<VerificationPresenter, FragmentVerificationBinding> implements VerificationView, View.OnClickListener {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_verification;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.sendSmsVerification();
        binding.btnConfirm.setOnClickListener(this);
        binding.btnSendAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                if(checkSmsConfirmationField()) {
                    presenter.confirmSmsVerification(binding.etConfirmationCode.getText().toString());
                }
                break;

            case R.id.btn_send_again:
                presenter.sendSmsVerification();
                break;
        }
    }

    @Override
    public void onSendVerificationSmsSuccess() {
        showMessage(getString(R.string.confirmation_code_sent_successful));
    }

    @Override
    public void onSendVerificationSmsFailed() {
        showMessage("Не удалось отправить код подтверждения");
    }

    @Override
    public void onVerificationSuccess() {
        MainActivity.open(getActivity(), true);
    }

    @Override
    public void onVerificationFailed() {
        binding.etConfirmationCode.setError(getString(R.string.error_confirmation_code));
    }

    private boolean checkSmsConfirmationField(){
        boolean success = true;

        if(binding.etConfirmationCode.getText().toString().isEmpty()){
            success = false;
            binding.etConfirmationCode.setError(getString(R.string.field_error_cannot_be_empty));

        } else if(binding.etConfirmationCode.getText().toString().length() < 4){
            success = false;
            binding.etConfirmationCode.setError(getString(R.string.field_error_length, 4));
        }

        return success;
    }
}
