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

package com.jana.android.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.jana.android.core.BuildConfig;

public class Logger {

    public static String TAG;

    private static int TAG_PREFIX_LENGTH;

    private static int MAX_TAG_LENGTH;

    private Logger() {
    }

    public static void init(Context context, String prefix) {

        TAG = getTag(context, prefix);

        TAG_PREFIX_LENGTH = TAG.length();

        MAX_TAG_LENGTH = 35;
    }

    private static String getTag(Context context, String prefix) {

        StringBuilder tag = new StringBuilder(prefix);

        String pkgName = context.getPackageName();

        PackageManager pkgMgr = context.getPackageManager();

        PackageInfo pkgInfo;

        try {

            pkgInfo = pkgMgr.getPackageInfo(pkgName, 0);

        } catch (NameNotFoundException e) {

            Log.e(tag.toString(),
                    "ERROR: Failed to get version no. due to the following error : "
                            + e.getMessage());

            e.printStackTrace();

            return tag.toString();
        }

        tag.append("-").append(pkgInfo.versionCode).append(":");

        return tag.toString();
    }

    public static String makeTag(String str) {

        if (str.length() > MAX_TAG_LENGTH - TAG_PREFIX_LENGTH) {

            return TAG
                    + str.substring(0, MAX_TAG_LENGTH - TAG_PREFIX_LENGTH - 1);
        }

        return TAG + str;
    }

    @SuppressWarnings("rawtypes")
    public static String makeTag(Class cls) {

        return makeTag(cls.getSimpleName());
    }

    /**
     * Log as verbose
     *
     * @param msg
     */
    public static void v(String msg) {

        v("", msg);
    }

    /**
     * Log as verbose
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {

        if ((BuildConfig.DEBUG/* && Log.isLoggable(TAG, Log.VERBOSE) */)) {

            Log.v(TAG + tag, msg);
        }
    }

    /**
     * Log as debug
     *
     * @param msg
     */
    public static void d(String msg) {

        d("", msg);
    }

    /**
     * Log as debug
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {

        if (BuildConfig.DEBUG/* && Log.isLoggable(TAG, Log.DEBUG) */) {

            Log.d(TAG + tag, msg);
        }
    }

    /**
     * Log as information
     *
     * @param msg
     */
    public static void i(String msg) {

        i("", msg);
    }

    /**
     * Log as information
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {

        Log.i(TAG + tag, msg);
    }

    /**
     * Log as warning
     *
     * @param msg
     */
    public static void w(String msg) {

        w("", msg);
    }

    /**
     * Log as warning
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {

        Log.w(TAG + tag, msg);
    }

    /**
     * Log as error
     *
     * @param msg
     */
    public static void e(String msg) {

        e("", msg);
    }

    /**
     * Log as error
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {

        Log.e(TAG + tag, msg);
    }

}