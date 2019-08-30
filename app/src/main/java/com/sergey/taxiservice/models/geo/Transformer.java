package com.sergey.taxiservice.models.geo;

import com.sergey.taxiservice.models.geo.api.GeoData;
import com.sergey.taxiservice.models.geo.api.GeoHouse;
import com.sergey.taxiservice.models.geo.api.GeoObject;
import com.sergey.taxiservice.models.geo.api.GeoObjects;
import com.sergey.taxiservice.models.geo.api.GeoResponse;
import com.sergey.taxiservice.models.geo.api.GeoStreet;
import com.sergey.taxiservice.models.geo.api.GeoStreets;
import com.sergey.taxiservice.models.geo.db.GeoDbObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

public class Transformer {

    public static List<AddressModel> transform(RealmList<GeoDbObject> geoDbObjects) {
        List<AddressModel> list = new ArrayList<>();

        if(geoDbObjects != null) {
            for(GeoDbObject geoDbObject : geoDbObjects) {
                list.add(transform(geoDbObject));
            }
        }

        return list;
    }

    public static RealmList<GeoDbObject> transform(GeoData geoData) {
        RealmList<GeoDbObject> list = new RealmList<>();

        if(geoData != null && geoData.isSuccess() && geoData.getResponse() != null) {
            GeoResponse geoResponse = geoData.getResponse();

            if(geoResponse.getGeoStreets() != null) {
                list.addAll(transform(geoResponse.getGeoStreets()));
            }

            if(geoResponse.getGeoObjects() != null) {
                list.addAll(transform(geoResponse.getGeoObjects()));
            }
        }

        return list;
    }

    public static AddressModel transform(GeoDbObject geoDbObject) {
        AddressModel addressModel = new AddressModel();

        if(geoDbObject != null) {
            addressModel.setData(geoDbObject.getData());
            addressModel.setAdditionalData(geoDbObject.getAdditionalData());
            addressModel.setLat(geoDbObject.getLat());
            addressModel.setLon(geoDbObject.getLng());
        }

        return addressModel;
    }

    public static List<Map<String, Object>> transform(List<AddressModel> list) {
        List<Map<String, Object>> maps = new ArrayList<>();

        if(list != null) {
            for (AddressModel address : list) {
                maps.add(address.convertToRemoteModel());
            }
        }

        return maps;
    }

    private static RealmList<GeoDbObject> transform(GeoObjects geoObjects) {
        RealmList<GeoDbObject> list = new RealmList<>();

        if(geoObjects != null && geoObjects.getGeoObject() != null) {
            for(GeoObject geoObject : geoObjects.getGeoObject()) {
                list.add(transform(geoObject));
            }
        }

        return list;
    }

    private static RealmList<GeoDbObject> transform(GeoStreets geoStreets) {
        RealmList<GeoDbObject> list = new RealmList<>();

        if(geoStreets != null && geoStreets.getGeoStreet() != null) {
            for(GeoStreet geoStreet : geoStreets.getGeoStreet()) {
                list.addAll(transform(geoStreet));
            }
        }

        return list;
    }

    private static GeoDbObject transform(GeoObject geoObject) {
        GeoDbObject geoDbObject = new GeoDbObject();

        if(geoObject != null) {
            geoDbObject.setData(geoObject.getName());
            geoDbObject.setLat(geoObject.getLat());
            geoDbObject.setLng(geoObject.getLng());
        }

        return geoDbObject;
    }

    private static RealmList<GeoDbObject> transform(GeoStreet geoStreet) {
        RealmList<GeoDbObject> list = new RealmList<>();

        if(geoStreet != null && geoStreet.getHouses() != null) {
            for(GeoHouse house : geoStreet.getHouses()) {
                GeoDbObject geoDbObject = new GeoDbObject();
                geoDbObject.setData(geoStreet.getName());
                geoDbObject.setAdditionalData(house.getHouse());
                geoDbObject.setLat(house.getLat());
                geoDbObject.setLng(house.getLng());

                list.add(geoDbObject);
            }
        }

        return list;
    }
}
