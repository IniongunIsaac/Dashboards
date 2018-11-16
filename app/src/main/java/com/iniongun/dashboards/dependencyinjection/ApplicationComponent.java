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

package com.iniongun.dashboards.dependencyinjection;

import android.app.Application;

import com.iniongun.dashboards.ui.add_url.AddUrlActivity;
import com.iniongun.dashboards.ui.url_list.UrlListActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Annotated as a Singelton since we don't want to have multiple instances of a Single Database,
 * <p>
 * Created by R_KAY on 8/15/2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class, UrlModule.class})
public interface ApplicationComponent {

    void inject(UrlListActivity urlListActivity);
    void inject(AddUrlActivity addUrlActivity);

    Application application();

}
