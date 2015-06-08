/*
 * Copyright (C) 2013 OneTeam (IslamSamak : islamsamak01@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jana.android.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.os.Bundle;

import com.jana.android.core.app.config.Configuration;
import com.jana.android.core.utils.Logger;

public abstract class AbstractManager implements IManager,
        Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    protected boolean mInitialized;

    protected int activeActivitiesCount;

    protected AbstractManager() {
    }

    public void init(Configuration configs) {

        if (configs.isMonitored) {

            Logger.init(AbstractApplication.getApplication(),
                    AbstractApplication.getApplication().getAppTag());
        }

        Logger.d("Initializing App... !!");

        initDataProvider();

        BasicSettings settings = (BasicSettings) BasicSettings.getInstance();

        settings.init(AbstractApplication.getApplication());

        mInitialized = true;

    }

    public boolean isInitialized() {
        return mInitialized;
    }

    @Override
    public void initDataProvider() {
    }

    @Override
    public void clearMemoryCache() {
    }

    @Override
    public void clearStorageCache() {
    }

    @Override
    public void destory() {
    }

    @Override
    public void onConfigurationChanged(
            android.content.res.Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int level) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    synchronized public void onActivityStarted(Activity activity) {
        Logger.d("Starting activity " + activity.getClass().getSimpleName());
        ++activeActivitiesCount;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    synchronized public void onActivityStopped(Activity activity) {
        Logger.d("Stopping activity " + activity.getClass().getSimpleName());
        --activeActivitiesCount;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

}
