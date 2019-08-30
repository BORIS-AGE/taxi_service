package com.sergey.taxiservice.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.constants.CarType;
import com.sergey.taxiservice.constants.DateType;
import com.sergey.taxiservice.constants.PayType;
import com.sergey.taxiservice.ui.base.BaseDialog;
import com.sergey.taxiservice.ui.dialogs.views.ToggleButtonsManager;

public class OrderCarDialog extends BaseDialog {

    public static OrderCarDialog newInstance(int currentCar, int currentPay, int currentDate) {
        OrderCarDialog dialog = new OrderCarDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("currentCar", currentCar);
        bundle.putInt("currentPay", currentPay);
        bundle.putInt("currentDate", currentDate);
        dialog.setArguments(bundle);

        return dialog;
    }

    private DateType dateType = DateType.NOW;
    private PayType payType = PayType.CACHE;
    private CarType carType = CarType.UNIVERSAL;
    private OnEditedFinishedListener listener;

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_order_car;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToggleButtonsManager<DateType> dateManager = new ToggleButtonsManager<>(getLayoutInflater());
        dateManager.setOnItemChosenListener(type -> {
            dateType = type;
            if(listener != null && dateType == DateType.SELECT_DATE) {
                listener.dateSelected(dateType);
            }
        });

        ToggleButtonsManager<PayType> payManager = new ToggleButtonsManager<>(getLayoutInflater());
        payManager.setOnItemChosenListener(type -> {
            payType = type;
        });

        ToggleButtonsManager<CarType> carManager = new ToggleButtonsManager<>(getLayoutInflater());
        carManager.setOnItemChosenListener(type -> {
            carType = type;
        });

        dateManager.setUpLayout(DateType.values(), view.findViewById(R.id.dateTypesContainer), new float[]{1, 1}, getArguments().getInt("currentDate"));
        payManager.setUpLayout(PayType.values(), view.findViewById(R.id.payTypesContainer), new float[]{1, 1}, getArguments().getInt("currentPay"));
        carManager.setUpLayout(CarType.values(), view.findViewById(R.id.carTypesContainer), new float[]{0.7f, 1.5f}, getArguments().getInt("currentCar"));

        view.findViewById(R.id.btn_solo_ride).setOnClickListener(v -> {
            if(listener != null) {
                listener.onEditedFinished(dateType, carType, payType);
            }

            dismiss();
        });
    }

    public void setListener(OnEditedFinishedListener listener) {
        this.listener = listener;
    }

    public void show(FragmentManager manager) {
        super.show(manager, this.getClass().getCanonicalName());
    }

    public interface OnEditedFinishedListener {
        void onEditedFinished(DateType dateType, CarType carType, PayType payType);

        void dateSelected(DateType dateType);
    }
}
