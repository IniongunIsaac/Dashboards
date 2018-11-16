package com.iniongun.dashboards.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.iniongun.dashboards.database.UrlEntity;
import com.iniongun.dashboards.database.UrlRepository;

import java.util.List;

public class UrlListViewModel extends ViewModel {

    private UrlRepository repository;

    public UrlListViewModel(UrlRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<UrlEntity>> getUrls(){
        return repository.getUrls();
    }

    public void deleteUrl(UrlEntity urlEntity){

        new DeleteUrlTask().execute(urlEntity);

    }

    private class DeleteUrlTask extends AsyncTask<UrlEntity, Void, Void> {

        @Override
        protected Void doInBackground(UrlEntity... urlEntity) {
            repository.deleteUrlEntity(urlEntity[0]);
            return null;
        }
    }

}
