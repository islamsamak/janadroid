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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.jana.android.service.IListener;
import com.jana.android.service.IService;

import java.util.Hashtable;

public abstract class AbstractActivity extends ActionBarActivity implements
        IActivity {

    protected boolean mRequiresUpdateView = true;

    protected Hashtable<String, IListener> mListenerMap;

    protected Hashtable<String, IService> mServiceMap;

    protected BroadcastReceiver mUpdateScreenReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {

                Bundle extras = intent.getExtras();

                if (extras != null && !extras.isEmpty()) {

                    mRequiresUpdateView |= extras.getBoolean(
                            IActivity.EXTRA_REQUIRES_UPDATE_VIEW, false);
                }
            }

            boolean updated = onUpdateView(intent);

            if (updated) {
                mRequiresUpdateView = false;
            }
        }
    };

    public AbstractActivity() {

        mListenerMap = new Hashtable<String, IListener>();

        mServiceMap = new Hashtable<String, IService>();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        onCreateActivity(savedInstanceState);

        IntentFilter filter = new IntentFilter(ACTION_UPDATE_SCREEN);

        registerReceiver(mUpdateScreenReceiver, filter);
    }

    protected abstract void onCreateActivity(Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();

        // mRequiresUpdateView = true;
    }

    @Override
    public void addListener(IListener listener) {

        if (mListenerMap == null) {

            // FIXME: {Islam@240713}
            // Throw NullPointerException about the listener map
        }

        if (listener == null) {

            // FIXME: {Islam@240713}
            // Throw NullPointerException about the passed listener
        }

        mListenerMap.put(listener.getName(), listener);
    }

    @Override
    public void registerService(IService service) {
    }

    @Override
    public void unregisterService(IService service) {
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (mUpdateScreenReceiver != null) {
            unregisterReceiver(mUpdateScreenReceiver);
        }
    }

    @Override
    synchronized public boolean onUpdateView(Intent intent) {
        // These is just for adding synchronization over method
        return false;
    }

    public void setViewVisibility(int resid, boolean visible) {

        int state = visible ? View.VISIBLE : View.GONE;

        View view = findViewById(resid);

        if (view == null) {
            return;
        }

        view.setVisibility(state);
    }

    public boolean isViewVisible(int resid) {

        View view = findViewById(resid);

        if (view == null) {
            return false;
        }

        return view.getVisibility() == View.VISIBLE;
    }

}
