package com.sergey.taxiservice.di.modules;

import com.google.gson.Gson;
import com.sergey.taxiservice.api.Api;
import com.sergey.taxiservice.interactor.DataStore;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    DataStore provideDataStore(Api api, Gson gson, PreferencesManager preferencesManager) {
        return new DataStore(api, gson, preferencesManager);
    }
}
