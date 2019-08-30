package com.sergey.taxiservice.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        PointType.START_POINT,
        PointType.END_POINT
})
@Retention(RetentionPolicy.SOURCE)
public @interface PointType {
    int START_POINT = 0;
    int END_POINT = 1;
}
