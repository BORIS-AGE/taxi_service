package com.sergey.taxiservice.models.db;

import android.support.annotation.IntDef;

@IntDef({
        OrderType.COMPANION,
        OrderType.SINGLE
})
public @interface OrderType {
    int COMPANION = 0;
    int SINGLE = 1;
}
