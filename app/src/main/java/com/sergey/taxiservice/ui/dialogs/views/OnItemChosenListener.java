package com.sergey.taxiservice.ui.dialogs.views;

import com.sergey.taxiservice.EnumExtension;

public interface OnItemChosenListener<T extends EnumExtension> {

    void onItemChosen(T type);
}
