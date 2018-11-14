package com.iniongun.dashboards.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.res.Configuration;

public class DashboardApplication extends Application {

    public static DashboardApplication get(Activity activity){
        return (DashboardApplication) activity.getApplication();
    }

    public static DashboardApplication get(Service service){
        return (DashboardApplication) service.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
