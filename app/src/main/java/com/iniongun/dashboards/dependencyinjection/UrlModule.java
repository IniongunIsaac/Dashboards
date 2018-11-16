package com.iniongun.dashboards.dependencyinjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.iniongun.dashboards.database.UrlDao;
import com.iniongun.dashboards.database.UrlDatabase;
import com.iniongun.dashboards.database.UrlRepository;
import com.iniongun.dashboards.viewmodels.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UrlModule {

    private final UrlDatabase database;

    public UrlModule(Application application){
        this.database = Room.databaseBuilder(application, UrlDatabase.class, "UrlsDatabase.db").build();
    }

    @Provides
    @Singleton
    UrlRepository provideUrlRepository(UrlDao urlDao){
        return new UrlRepository(urlDao);
    }

    @Provides
    @Singleton
    UrlDao provideUrlDao(UrlDatabase database){
        return database.urlDao();
    }

    @Provides
    @Singleton
    UrlDatabase provideUrlDatabase(Application application){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(UrlRepository repository){
        return new CustomViewModelFactory(repository);
    }

}
