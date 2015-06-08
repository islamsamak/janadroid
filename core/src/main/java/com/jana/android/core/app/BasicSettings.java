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

import com.jana.android.core.utils.Logger;

/**
 * Settings is the responsible for manipulating app settings for all different
 * states
 *
 * @author IslamSamak : islamsamak01@gmail.com
 */
public class BasicSettings extends AbstractSettings {

    protected BasicSettings(Context context) {
        super(context);
    }

    public static AbstractSettings getInstance() throws IllegalStateException {

        if (sInstance == null) {
            Logger.w("Settings is uninitialized");
            sInstance = new BasicSettings(AbstractApplication.getApplication());
        }

        return sInstance;
    }

    public void init(Context context) {

        Logger.v("Settings.init()");

        loadDefaults();
    }

    @Override
    protected void loadDefaults() {
    }

}
