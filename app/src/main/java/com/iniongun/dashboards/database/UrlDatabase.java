package com.iniongun.dashboards.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UrlEntity.class}, version = 1)
public abstract class UrlDatabase extends RoomDatabase {

    public abstract UrlDao urlDao();

}
