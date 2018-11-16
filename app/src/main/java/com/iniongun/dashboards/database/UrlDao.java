package com.iniongun.dashboards.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UrlDao {

    @Query("SELECT * FROM UrlEntity")
    LiveData<List<UrlEntity>> getUrls();

    @Insert(onConflict = REPLACE)
    Long insertUrl(UrlEntity urlEntity);

    @Delete
    void deleteUrl(UrlEntity urlEntity);

}
