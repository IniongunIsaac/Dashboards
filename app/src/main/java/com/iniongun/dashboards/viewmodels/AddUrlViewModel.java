package com.iniongun.dashboards.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.iniongun.dashboards.database.UrlEntity;
import com.iniongun.dashboards.database.UrlRepository;

public class AddUrlViewModel extends ViewModel {

    private UrlRepository repository;

    public AddUrlViewModel(UrlRepository repository) {
        this.repository = repository;
    }

    public void addUrl(UrlEntity urlEntity){
        new AddUrlTask().execute(urlEntity);
    }

    private class AddUrlTask extends AsyncTask<UrlEntity, Void, Void> {

        @Override
        protected Void doInBackground(UrlEntity... urlEntity) {
            Long id = repository.createUrlEntity(urlEntity[0]);
            return null;
        }
    }

}
