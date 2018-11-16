package com.iniongun.dashboards.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UrlEntity {

    @PrimaryKey(autoGenerate = true)
    public int urlId;

    public String url;

    public UrlEntity(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
