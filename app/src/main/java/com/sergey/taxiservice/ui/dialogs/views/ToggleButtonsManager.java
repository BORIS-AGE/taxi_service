package com.sergey.taxiservice.ui.dialogs.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sergey.taxiservice.EnumExtension;
import com.sergey.taxiservice.R;

import java.util.ArrayList;
import java.util.List;

public class ToggleButtonsManager<T extends EnumExtension> {

    private LayoutInflater inflater;
    private OnItemChosenListener<T> onItemChosenListener;

    public ToggleButtonsManager(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setUpLayout(T[] enumTypes, LinearLayout layout, float[] scales, int current) {
        List<ToggleButton> toggles = new ArrayList<>();
        enumTypes[current].setState(true);

        if(onItemChosenListener != null) {
            onItemChosenListener.onItemChosen(enumTypes[0]);
        }

        for(T type : enumTypes) {
            View view = inflater.inflate(R.layout.item_dialog_select, null);
            TextView name = view.findViewById(R.id.name);
            name.setText(type.getTitle());

            ToggleButton logo = view.findViewById(R.id.image);
            logo.setBackground(getImage(inflater.getContext(), type.getImage()));
            updateLayout(logo, scales);
            logo.setChecked(type.getState());
            logo.setClickable(false);

            View.OnTouchListener listener = (v, event) -> {
                if(!type.getState() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    clearStates(enumTypes, toggles);
                    type.setState(true);
                    logo.setChecked(true);

                    if(onItemChosenListener != null) {
                        onItemChosenListener.onItemChosen(type);
                    }
                }

                return false;
            };

            toggles.add(logo);
            layout.addView(view);

            view.setOnTouchListener(listener);
            logo.setOnTouchListener(listener);
        }
    }

    public void setOnItemChosenListener(OnItemChosenListener<T> onItemChosenListener) {
        this.onItemChosenListener = onItemChosenListener;
    }

    private void clearStates(T[] enumTypes, List<ToggleButton> toggles) {
        for(int i = 0; i < toggles.size(); i++) {
            enumTypes[i].setState(false);
            toggles.get(i).setChecked(false);
        }
    }

    private void updateLayout(View view, float[] scales) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int)(params.height * scales[0]);
        params.width = (int)(params.width * scales[1]);
        view.setLayoutParams(params);
    }

    private Drawable getImage(Context context, @DrawableRes int drawableId) {
        return context.getResources().getDrawable(drawableId);
    }
}
