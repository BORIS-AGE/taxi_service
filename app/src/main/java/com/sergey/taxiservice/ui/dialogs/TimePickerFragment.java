package com.sergey.taxiservice.ui.dialogs;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends TimePickerDialog {

    public TimePickerFragment(Context context, OnTimeChooseListener listener) {
        super(context, listener, 0, 0, true);
    }

    public static class OnTimeChooseListener implements OnTimeSetListener {

        private Date date;
        private OnTimeSelectedListener listener;

        public OnTimeChooseListener(Date date, OnTimeSelectedListener listener) {
            this.date = date;
            this.listener = listener;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            if(listener != null) {
                listener.onTimeSelected(calendar.getTime());
            }
        }
    }

    public interface OnTimeSelectedListener {

        void onTimeSelected(Date date);
    }
}
