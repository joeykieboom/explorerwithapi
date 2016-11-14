package com.skiefboom.cocktailexplorerwithapi;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.skiefboom.cocktailexplorerwithapi.data.api.Api;
import com.skiefboom.cocktailexplorerwithapi.helpers.PersistentHelper;

public class CocktailExplorerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
        PersistentHelper.initialize(getApplicationContext());
        Api.initialize();
    }
}
