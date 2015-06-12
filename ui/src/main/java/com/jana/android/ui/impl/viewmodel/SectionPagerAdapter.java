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

package com.jana.android.ui.impl.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author islam
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private List<Class<?>> mClassesList;

    private List<Bundle> mExtrasList;

    private List<String> mTitlesList;

    public SectionPagerAdapter(Context context, FragmentManager fm,
                               List<Class<?>> classes) {

        super(fm);

        mContext = context;

        mClassesList = classes;
    }

    public void setFragmentExtras(List<Bundle> extras) {
        mExtrasList = extras;
    }

    public void setFragmentTitles(List<String> titles) {
        mTitlesList = titles;
    }

    @Override
    public Fragment getItem(int position) {

        if (mClassesList == null) {
            return null;
        }

        Class<?> clss = mClassesList.get(position);

        Bundle args = null;

        if (mExtrasList != null) {
            args = mExtrasList.get(position);
        }

        Fragment fragment = Fragment
                .instantiate(mContext, clss.getName(), args);

        return fragment;
    }

    @Override
    public int getCount() {

        if (mClassesList != null) {

            return mClassesList.size();
        }

        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (mTitlesList != null) {
            return mTitlesList.get(position);
        }

        return super.getPageTitle(position);
    }

}
