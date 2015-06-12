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

package com.jana.android.net;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author islam
 */
public class Connectivity {

    public boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null) {

                if (info.isConnected() && info.isAvailable()) {

                    return true;
                }
            }
        }

        return false;
    }

    public AlertDialog createWarnNoInternetAccess(Context context) {
        return createWarning(context, R.mipmap.ic_launcher,
                R.string.title_no_internet_access,
                R.string.msg_no_internet_access);
    }

    public AlertDialog createWarning(Context context, int icon, int title,
                                     int msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);

        alertDialog.setMessage(context.getString(msg));

        alertDialog.setIcon(icon);

        return alertDialog;
    }

}
