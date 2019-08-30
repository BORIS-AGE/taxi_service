package com.sergey.taxiservice.manager.location;

import com.sergey.taxiservice.manager.location.listener.OnLocationChangeListener;

public interface LocationManager {

    void init();

    void setChangeListener(OnLocationChangeListener listener);
}
