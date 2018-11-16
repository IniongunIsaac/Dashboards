package com.iniongun.dashboards.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

public class UrlRepository {

    private final UrlDao urlDao;

    @Inject
    public UrlRepository(UrlDao urlDao) {
        this.urlDao = urlDao;
    }

    public LiveData<List<UrlEntity>> getUrls(){
        return urlDao.getUrls();
    }

    public Long createUrlEntity(UrlEntity urlEntity){
        return urlDao.insertUrl(urlEntity);
    }

    public void deleteUrlEntity(UrlEntity urlEntity){
        urlDao.deleteUrl(urlEntity);
    }
}
