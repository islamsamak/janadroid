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

package com.jana.android.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jana.android.task.DefaultAsyncTask;
import com.jana.android.ui.impl.activity.DefaultActivity;

/**
 * @author islam
 */
public abstract class AbstractSplashActivity<P, G, R> extends DefaultActivity {

    protected Class<? extends Activity> mTargetClass;

    protected Bundle mTargetExtras;

    protected BackgroundTask mBackgroundTask;

    protected boolean autoStartTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        super.onCreate(savedInstanceState);

        startBackgroundTask();
    }

    protected void startBackgroundTask() {

        if (mBackgroundTask != null && !mBackgroundTask.isCancelled()) {
            mBackgroundTask.cancel(true);
        }

        mBackgroundTask = new BackgroundTask();

        P[] params = getTaskParams();

        mBackgroundTask.execute(params);
    }

    protected void onCreateActivity(Bundle savedInstanceState) {
    }

    /**
     *
     */
    protected void startTarget() {

        if (mTargetClass == null) {
            throw new IllegalStateException("No target activity is specified");
        }

        startTarget(mTargetClass);
    }

    protected void startTarget(Class<? extends Activity> clazz) {
        // Start a new activity
        Intent intent = new Intent(AbstractSplashActivity.this, clazz);

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (mTargetExtras != null) {
            intent.putExtras(mTargetExtras);
        }

        startActivity(intent);

        // overridePendingTransition(android.R.anim.slide_out_right,
        // android.R.anim.slide_in_left);

        // overridePendingTransition(android.R.anim.slide_in_left,
        // android.R.anim.slide_out_right);

        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

        finish();
    }

    public void setTargetClass(Class<? extends Activity> targetClass) {
        mTargetClass = targetClass;
    }

    public void setTargetExtras(Bundle targetExtras) {
        mTargetExtras = targetExtras;
    }

    public void setAutoStartTarget(boolean enabled) {
        this.autoStartTarget = enabled;
    }

    abstract public P[] getTaskParams();

    abstract public void onPreExecute();

    abstract public void onProgressUpdate(G[] progress);

    abstract public R doInBackground(P... params);

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mBackgroundTask.cancel(true);

        this.finish();
    }

    protected class BackgroundTask extends DefaultAsyncTask<P, G, R> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            AbstractSplashActivity.this.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(G... progress) {

            super.onProgressUpdate(progress);

            AbstractSplashActivity.this.onProgressUpdate(progress);
        }

        @Override
        protected R doInBackground(P... params) {

            synchronized (this) {
                return AbstractSplashActivity.this.doInBackground(params);
            }
        }

        @Override
        protected void onPostExecute(R result) {

            super.onPostExecute(result);

            if (autoStartTarget) {
                startTarget();
            }
        }
    }

}
