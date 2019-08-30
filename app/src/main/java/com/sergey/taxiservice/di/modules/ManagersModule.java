package com.sergey.taxiservice.di.modules;

import android.content.Context;

import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.manager.preferences.PreferencesManagerImpl;
import com.sergey.taxiservice.manager.resources.ResourcesManager;
import com.sergey.taxiservice.manager.resources.ResourcesManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagersModule {

    @Provides
    @Singleton
    PreferencesManager providePreferencesManager(Context context){
        return new PreferencesManagerImpl(context);
    }

    @Provides
    @Singleton
    ResourcesManager resourcesManager(Context context) {
        return new ResourcesManagerImpl(context);
    }
}
