package com.jakewharton.demo.jakewhartondemo.modules_components;

import android.app.Application;

import com.jakewharton.demo.jakewhartondemo.database.RealmController;

import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Provides
    public RealmController provideDBHandler(Application application) {
        return RealmController.with(application);
    }
}
