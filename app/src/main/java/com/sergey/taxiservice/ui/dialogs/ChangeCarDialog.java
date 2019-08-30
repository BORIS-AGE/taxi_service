package com.sergey.taxiservice.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.constants.CarType;
import com.sergey.taxiservice.ui.base.BaseDialog;
import com.sergey.taxiservice.ui.dialogs.views.ToggleButtonsManager;

public class ChangeCarDialog extends BaseDialog {

    private OnCarChosenListener listener;

    public static ChangeCarDialog newInstance(int current) {
        ChangeCarDialog dialog = new ChangeCarDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("current", current);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_change_car;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToggleButtonsManager<CarType> carManager = new ToggleButtonsManager<>(getLayoutInflater());
        carManager.setUpLayout(CarType.values(), view.findViewById(R.id.carTypesContainer), new float[]{0.7f, 1.5f}, getArguments().getInt("current"));
        carManager.setOnItemChosenListener(type -> {
            if(listener != null)
                listener.onCarChosen(type);
            dismiss();
        });
    }

    public void setListener(OnCarChosenListener listener) {
        this.listener = listener;
    }

    public interface OnCarChosenListener {
        void onCarChosen(CarType type);
    }
}
