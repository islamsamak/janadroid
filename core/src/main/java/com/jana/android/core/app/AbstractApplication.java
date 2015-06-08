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
import android.content.res.Configuration;
import android.os.Bundle;

import com.jana.android.core.utils.Logger;

public abstract class AbstractApplication<T extends AbstractManager> extends
        Application implements Application.ActivityLifecycleCallbacks {

    private static AbstractApplication<? extends AbstractManager> sInstance;

    private T appManager;

    public static AbstractApplication<? extends AbstractManager> getApplication() {
        return sInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        String tag = getAppTag();

        onCreateApplication(tag);
    }

    protected abstract String getAppTag();

    protected void setAppManager(T manager) {
        this.appManager = manager;
    }

    protected void onCreateApplication(String tag) {

        sInstance = this;

        Logger.init(this, tag);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (appManager != null) {
            appManager.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (appManager != null) {
            appManager.onTrimMemory(level);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (appManager != null) {
            appManager.onActivityCreated(activity, savedInstanceState);
        }
    }

    @Override
    synchronized public void onActivityStarted(Activity activity) {
        if (appManager != null) {
            appManager.onActivityStarted(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (appManager != null) {
            appManager.onActivityResumed(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (appManager != null) {
            appManager.onActivityPaused(activity);
        }
    }

    @Override
    synchronized public void onActivityStopped(Activity activity) {
        if (appManager != null) {
            appManager.onActivityStopped(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (appManager != null) {
            appManager.onActivitySaveInstanceState(activity, outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (appManager != null) {
            appManager.onActivityDestroyed(activity);
        }
    }

}