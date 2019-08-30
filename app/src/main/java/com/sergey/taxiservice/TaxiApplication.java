package com.sergey.taxiservice;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.sergey.taxiservice.di.component.DaggerApplicationComponent;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TaxiApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        Realm.init(this);

        DaggerApplicationComponent.builder()
                .context(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public static Realm getDbInstance() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("localData.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config);
    }
}
