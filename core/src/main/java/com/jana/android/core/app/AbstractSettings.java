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

import android.content.Context;
import android.content.SharedPreferences;

import com.jana.android.core.R;
import com.jana.android.core.utils.DeviceInfo;
import com.jana.android.core.utils.Logger;

import java.util.Date;
import java.util.Locale;

/**
 * Settings is the responsible for manipulating app settings for all different
 * states
 *
 * @author IslamSamak : islamsamak01@gmail.com
 */
public abstract class AbstractSettings {

    protected static AbstractSettings sInstance;

    protected final String PREFERENCE_NAME;

    protected AbstractSettings(Context context) {
        PREFERENCE_NAME = context.getPackageName() + "_preferences";
    }

    protected void init(Context context) {
        Logger.v("Settings.init()");

        loadDefaults();
    }

    protected void loadDefaults() {

        sInstance.setInitTime(sInstance.getInitTime());

        sInstance.setDeviceId(getDeviceId());

        sInstance.setLocale(sInstance.getLocale());
    }

    /*-------- HELPER METHODS --------*/
    private SharedPreferences getSharedPreferences() {

        SharedPreferences prefs = AbstractApplication.getApplication()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return prefs;
    }

    public boolean contains(int id) {

        String key = AbstractApplication.getApplication().getString(id);

        return contains(key);
    }

    public boolean contains(String key) {

        SharedPreferences prefs = getSharedPreferences();

        boolean found = prefs.contains(key);

        return found;
    }

    public SharedPreferences.Editor remove(int id) {

        String key = AbstractApplication.getApplication().getString(id);

        return remove(key);
    }

    public SharedPreferences.Editor remove(String key) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.remove(key);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putBoolean(key, value);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putBoolean(int id, boolean value) {

        String key = AbstractApplication.getApplication().getString(id);

        return putBoolean(key, value);
    }

    public SharedPreferences.Editor putInt(String key, int value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putInt(key, value);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putInt(int id, int value) {

        String key = AbstractApplication.getApplication().getString(id);

        return putInt(key, value);
    }

    public SharedPreferences.Editor putFloat(String key, float value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putFloat(key, value);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putFloat(int id, float value) {

        String key = AbstractApplication.getApplication().getString(id);

        return putFloat(key, value);
    }

    public SharedPreferences.Editor putLong(String key, long value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putLong(key, value);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putLong(int id, long value) {

        String key = AbstractApplication.getApplication().getString(id);

        return putLong(key, value);
    }

    public SharedPreferences.Editor putString(String key, String value) {

        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putString(key, value);

        editor.commit();

        return editor;
    }

    public SharedPreferences.Editor putString(int id, String value) {

        String key = AbstractApplication.getApplication().getString(id);

        return putString(key, value);
    }

    public boolean getBoolean(String key, boolean defValue) {

        boolean value = getSharedPreferences().getBoolean(key, defValue);

        return value;
    }

    public boolean getBoolean(int id, boolean defValue) {

        String key = AbstractApplication.getApplication().getString(id);

        return getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {

        int value = getSharedPreferences().getInt(key, defValue);

        return value;
    }

    public int getInt(int id, int defValue) {

        String key = AbstractApplication.getApplication().getString(id);

        return getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {

        float value = getSharedPreferences().getFloat(key, defValue);

        return value;
    }

    public float getFloat(int id, float defValue) {

        String key = AbstractApplication.getApplication().getString(id);

        return getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {

        long value = getSharedPreferences().getLong(key, defValue);

        return value;
    }

    public long getLong(int id, long defValue) {

        String key = AbstractApplication.getApplication().getString(id);

        return getLong(key, defValue);
    }

    public String getString(String key, String defValue) {

        String value = getSharedPreferences().getString(key, defValue);

        return value;
    }

    public String getString(int id, String defValue) {

        String key = AbstractApplication.getApplication().getString(id);

        return getString(key, defValue);
    }

    public long getInitTime() {
        long value = getLong(R.string.pref_init_time, new Date().getTime());
        return value;
    }

    public void setInitTime(long initTime) {
        putLong(R.string.pref_init_time, initTime);
    }

    public String getLocale() {
        String value = getString(R.string.pref_locale, Locale.getDefault()
                .getLanguage());
        return value;
    }

    public void setLocale(String locale) {
        putString(R.string.pref_locale, locale);
    }

    public boolean isArabicLocale() {
        return getLocale().startsWith("ar");
    }

    public boolean isRTL() {
        Locale locale = new Locale(getLocale());
        String name = locale.getDisplayName(locale);
        final int directionality = Character.getDirectionality(name.charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT
                || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public String getDeviceId() {
        String value = getString(R.string.pref_device_id,
                DeviceInfo.getUniqueId());
        return value;
    }

    private void setDeviceId(String id) {
        putString(R.string.pref_device_id, id);
    }

}
