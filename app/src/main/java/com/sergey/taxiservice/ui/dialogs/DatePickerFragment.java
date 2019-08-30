package com.sergey.taxiservice.ui.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private onDateListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE, 7);
        dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        return dpd;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(listener != null) {
            listener.onDateSet(calendar.getTime());
        }
    }

    public void setListener(onDateListener listener) {
        this.listener = listener;
    }

    public void show(FragmentManager manager) {
        super.show(manager, this.getClass().getCanonicalName());
    }

    public interface onDateListener {
        void onDateSet(Date date);
    }
}
