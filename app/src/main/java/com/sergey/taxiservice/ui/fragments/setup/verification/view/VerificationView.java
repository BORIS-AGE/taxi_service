package com.sergey.taxiservice.ui.fragments.setup.verification.view;

import com.sergey.taxiservice.ui.base.BaseView;

public interface VerificationView extends BaseView {

    void onSendVerificationSmsSuccess();

    void onSendVerificationSmsFailed();

    void onVerificationSuccess();

    void onVerificationFailed();
}
