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

package com.jana.android.ui.impl.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.jana.android.net.Connectivity;
import com.jana.android.service.OnNetworkStateChangedListener;
import com.jana.android.ui.R;
import com.jana.android.ui.activity.AbstractActivity;
import com.jana.android.utils.Logger;

/**
 * @author islam
 */
public class DefaultActivity extends AbstractActivity implements
        OnNetworkStateChangedListener {

    private BroadcastReceiver mNetworkStateReceiver = null;

    private OnNetworkStateChangedListener mNetworkStateChangedListener = null;

    /*
     * (non-Javadoc)
     *
     * @see com.jana.android.ui.activity.AbstractActivity#
     * onCreateActivity(android.os.Bundle)
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mNetworkStateChangedListener != null) {

            final Connectivity connectivity = new Connectivity();

            if (!connectivity.isConnected(getApplication())) {

                showNoInternetAccessWarning();

                Logger.v("DefaultActivity::onStart[]: Connectivity warning dialog is shown successfully");
            }
        }
    }

    /**
     * @return the mNetworkStateChangedListener
     */
    public OnNetworkStateChangedListener getNetworkStateChangedListener() {
        return mNetworkStateChangedListener;
    }

    /**
     * @param listener the mNetworkStateChangedListener to set
     */
    public void setNetworkStateChangedListener(
            OnNetworkStateChangedListener listener) {
        mNetworkStateChangedListener = listener;
    }

    public void setupNetworkStateReceiver() {

        if (mNetworkStateReceiver != null) {
            return;
        }

        mNetworkStateReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent == null || intent.getExtras() == null) {
                    Logger.d("Invalid change in network state. No intent data or no extras found.");
                    return;
                }

                Bundle extras = intent.getExtras();

                @SuppressWarnings("deprecation")
                NetworkInfo networkInfo = extras
                        .getParcelable(ConnectivityManager.EXTRA_NETWORK_INFO);

                if (networkInfo == null) {

                    Logger.d("No network state info is found.");

                    return;
                }

                // Check the type of the connectivity
                int type = networkInfo.getType();

                if (mNetworkStateChangedListener == null) {
                    return;
                }

                mNetworkStateChangedListener.onChange(type, networkInfo);
            }
        };

        registerReceiver(mNetworkStateReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void showNoInternetAccessWarning() {

        final Connectivity connectivity = new Connectivity();

        AlertDialog alertDialog = connectivity.createWarning(
                DefaultActivity.this, android.R.drawable.ic_dialog_alert,
                R.string.title_no_internet_access,
                R.string.msg_no_internet_access);

        String btnName = getString(android.R.string.ok);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, btnName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DefaultActivity.this.finish();
                    }
                });

        alertDialog.setCancelable(false);

        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mNetworkStateReceiver != null) {

            try {

                unregisterReceiver(mNetworkStateReceiver);

            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onChange(int type, NetworkInfo networkInfo) {

        if (ConnectivityManager.TYPE_MOBILE == type
                || ConnectivityManager.TYPE_WIFI == type
                || ConnectivityManager.TYPE_WIMAX == type) {

            boolean isConnected = networkInfo.isConnectedOrConnecting();

            final Connectivity connectivity = new Connectivity();

            isConnected |= connectivity.isConnected(getApplication());

            if (!isConnected) {

                showNoInternetAccessWarning();
            }
        }
    }

}
