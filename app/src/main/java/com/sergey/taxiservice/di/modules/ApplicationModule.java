package com.sergey.taxiservice.di.modules;

import com.sergey.taxiservice.di.scopes.ActivityScope;
import com.sergey.taxiservice.ui.activities.LoginActivity;
import com.sergey.taxiservice.ui.activities.MainActivity;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class, ApiModule.class, AppModule.class, ManagersModule.class})
public interface ApplicationModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    LoginActivity loginActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    MainActivity mainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = {ActivityModule.class})
    ToolbarActivity toolbarActivityInjector();
}
