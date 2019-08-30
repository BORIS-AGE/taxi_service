package com.sergey.taxiservice.ui.dialogs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BaseDialog;

import java.util.ArrayList;

public class InputAddressesDialog extends BaseDialog {

    private static final String ADDRESSES = "addresses";

    // references for updating an item that was chosen
    private AddressModel currentAddress;
    private LinearLayout addressesContainer;
    private OnDialogActionListener actionListener;
    private ArrayList<AddressModel> addresses;

    public static InputAddressesDialog newInstance(ArrayList<AddressModel> addresses) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ADDRESSES, addresses);

        InputAddressesDialog dialog = new InputAddressesDialog();
        dialog.setArguments(arguments);

        return dialog;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_input_addresses;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            addresses = getArguments().getParcelableArrayList(ADDRESSES);

            if(addresses == null)
                addresses = new ArrayList<>();

            if(addresses.size() == 1) {
                addresses.add(new AddressModel());
            } else if(addresses.size() < 2) {
                addresses.add(new AddressModel());
                addresses.add(new AddressModel());
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addressesContainer = view.findViewById(R.id.addressesContainer);

        View btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if(isFieldsEmpty()) {
                showMessage(R.string.message_text_empty_input_forms);

            } else {
                if(actionListener != null) {
                    actionListener.onTripEdited(addresses);
                }

                dismiss();
            }
        });

        View btnAddPoint = view.findViewById(R.id.btnAddPoint);
        btnAddPoint.setOnClickListener(v -> {
            if(isFieldsEmpty()) {
                showMessage(R.string.message_text_empty_input_forms);

            } else {
                addresses.add(new AddressModel());
                reloadInputForms();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadInputForms();
    }

    public void update(AddressModel addressModel) {
        currentAddress.setData(addressModel.getData());
        currentAddress.setAdditionalData(addressModel.getAdditionalData());
        currentAddress.setLon(addressModel.getLon());
        currentAddress.setLat(addressModel.getLat());

        reloadInputForms();
    }

    public void show(FragmentManager manager) {
        super.show(manager, this.getClass().getCanonicalName());
    }

    public void setOnDialogActionListener(OnDialogActionListener listener) {
        this.actionListener = listener;
    }

    @SuppressLint("InflateParams")
    private void reloadInputForms() {
        addressesContainer.removeAllViews();

        for(int i = 0; i < addresses.size(); i++) {
            AddressModel address = addresses.get(i);
            View inputForm = getLayoutInflater().inflate(R.layout.item_input_address, null);

            AppCompatEditText inputField = inputForm.findViewById(R.id.inputField);
            inputField.setInputType(InputType.TYPE_NULL);
            inputField.setOnClickListener(v -> {
                if(actionListener != null) {
                    currentAddress = address;
                    actionListener.onInputFormSelected(currentAddress);
                }
            });

            if(address.getTitle() != null && !address.getTitle().isEmpty()) {
                inputField.setText(address.getTitle());
            } else {
                if(i == 0)
                    inputField.setHint(R.string.view_text_from);
                else inputField.setHint(R.string.view_text_to);
            }

            addressesContainer.addView(inputForm);
        }
    }

    private boolean isFieldsEmpty() {
        for(AddressModel address : addresses) {
            if(address.getTitle() == null || address.getTitle().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public interface OnDialogActionListener {

        void onInputFormSelected(AddressModel address);

        void onTripEdited(ArrayList<AddressModel> addresses);
    }
}
