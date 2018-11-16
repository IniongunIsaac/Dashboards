package com.iniongun.dashboards.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.res.Configuration;

import com.iniongun.dashboards.dependencyinjection.ApplicationComponent;
import com.iniongun.dashboards.dependencyinjection.ApplicationModule;
import com.iniongun.dashboards.dependencyinjection.DaggerApplicationComponent;
import com.iniongun.dashboards.dependencyinjection.UrlModule;

public class DashboardApplication extends Application {

    private ApplicationComponent applicationComponent;

    public static DashboardApplication get(Activity activity){
        return (DashboardApplication) activity.getApplication();
    }

    public static DashboardApplication get(Service service){
        return (DashboardApplication) service.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .urlModule(new UrlModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
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
