package com.sergey.taxiservice.constants;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.sergey.taxiservice.EnumExtension;
import com.sergey.taxiservice.R;

public enum DateType implements EnumExtension {
    NOW         (R.drawable.ic_circle, R.string.view_text_now),
    SELECT_DATE (R.drawable.ic_circle, R.string.view_text_select_date);

    private int imageResId;
    private int stringResId;
    private boolean state;

    DateType(@DrawableRes int imageResId, @StringRes int stringResId) {
        this.imageResId = imageResId;
        this.stringResId = stringResId;
        this.state = false;
    }

    @Override
    public int getImage() {
        return imageResId;
    }

    @Override
    public int getTitle() {
        return stringResId;
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean state) {
        this.state = state;
    }
}
