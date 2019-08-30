package com.sergey.taxiservice.constants;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.sergey.taxiservice.EnumExtension;
import com.sergey.taxiservice.R;

public enum CarType implements EnumExtension {
//    HATCHBACK   (R.drawable.ic_hatchback,   R.string.message_car_type_hatchback),
    SEDAN       (R.drawable.ic_sedan,       R.string.message_car_type_sedan),
    UNIVERSAL   (R.drawable.ic_universal,   R.string.message_car_type_universal),
//    MINIVAN     (R.drawable.ic_minivan,     R.string.message_car_type_minivan),
    MINIBUS     (R.drawable.ic_minibus,     R.string.message_car_type_minibus),
    PREMIUM     (R.drawable.ic_premium,     R.string.message_car_type_premium);

    private int imageResId;
    private int stringResId;
    private boolean state;

    CarType(@DrawableRes int imageResId, @StringRes int stringResId) {
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
