package com.jakewharton.demo.jakewhartondemo;

import android.app.Application;

import com.jakewharton.demo.jakewhartondemo.modules_components.ApplicationComponent;
import com.jakewharton.demo.jakewhartondemo.modules_components.ApplicationModule;
import com.jakewharton.demo.jakewhartondemo.modules_components.DaggerApplicationComponent;
import com.jakewharton.demo.jakewhartondemo.modules_components.NetworkApiModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JakeWhartonApplication extends Application{
    private ApplicationComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkApiModule(new NetworkApiModule())
                .build();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
