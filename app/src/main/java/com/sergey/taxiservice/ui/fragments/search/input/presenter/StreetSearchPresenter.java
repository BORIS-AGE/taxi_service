package com.sergey.taxiservice.ui.fragments.search.input.presenter;

import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.models.geo.AddressModel;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.search.input.view.StreetSearchView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class StreetSearchPresenter extends BasePresenter<StreetSearchView> {

    private DataStore dataStore;

    @Inject
    StreetSearchPresenter(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void setSearchTextListening(EditText editText){
        RxTextView.textChanges(editText)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence) && charSequence.length() >= 2)
                .map(CharSequence::toString)
                .flatMap(dataStore::searchGeoData)
                .map(this::generateStreetSet)
                .subscribe(getView()::onSearchSuccess, getView()::showError);
    }

    private List<AddressModel> generateStreetSet(List<AddressModel> allAddresses) {
        List<AddressModel> addresses = new ArrayList<>();
        Set<String> streetNameSet = new HashSet<>();

        for(AddressModel address : allAddresses) {
            if(!streetNameSet.contains(address.getData())) {
                streetNameSet.add(address.getData());
                addresses.add(address);
            }
        }

        return addresses;
    }
}
