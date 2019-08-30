package com.sergey.taxiservice.ui.fragments.search.entire.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.models.geo.Transformer;
import com.sergey.taxiservice.models.geo.db.GeoDbObject;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.search.entire.view.AddressEntireView;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class AddressEntirePresenter extends BasePresenter<AddressEntireView> {

    @Inject
    AddressEntirePresenter() {}

    public void processGeoStreet(@NonNull String streetName, @NonNull String house, @Nullable String entrance) {
        Realm realm = TaxiApplication.getDbInstance();
        List<GeoDbObject> list = realm.where(GeoDbObject.class)
                .equalTo("data", streetName)
                .findAll();

        if(list.size() > 0) {
            GeoDbObject model = list.get(0);
            for(GeoDbObject geoDbObject : list) {
                if(geoDbObject.getAdditionalData().equals(house)) {
                    model = geoDbObject;
                    break;
                }
            }

            AddressModel addressModel = Transformer.transform(model);
            if(entrance != null && !entrance.isEmpty() &&
                    addressModel.getAdditionalData() != null && !addressModel.getAdditionalData().isEmpty()) {
                addressModel.setAdditionalData(addressModel.getAdditionalData() + ", " + entrance);
            }

            getView().onAddressReady(addressModel);
        }
    }

    public void processGeoObject(@NonNull String objectName) {
        Realm realm = TaxiApplication.getDbInstance();
        GeoDbObject geoDbObject = realm.where(GeoDbObject.class)
                .equalTo("data", objectName)
                .findFirst();

        if(geoDbObject != null) {
            AddressModel addressModel = Transformer.transform(geoDbObject);
            getView().onAddressReady(addressModel);
        }
    }
}
