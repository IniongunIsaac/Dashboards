/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.iniongun.dashboards.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.iniongun.dashboards.database.UrlRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final UrlRepository repository;

    @Inject
    public CustomViewModelFactory(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UrlListViewModel.class))
            return (T) new UrlListViewModel(repository);

        else if (modelClass.isAssignableFrom(AddUrlViewModel.class))
            return (T) new AddUrlViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
