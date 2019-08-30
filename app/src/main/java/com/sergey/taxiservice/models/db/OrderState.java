package com.sergey.taxiservice.models.db;

import android.support.annotation.IntDef;

@IntDef({
    OrderState.SEARCH_COMPANION,
    OrderState.DRIVER_SEARCHING,
    OrderState.CAR_FOUND
})
public @interface OrderState {
    int SEARCH_COMPANION = 0;
    int DRIVER_SEARCHING = 1;
    int CAR_FOUND = 2;
}
