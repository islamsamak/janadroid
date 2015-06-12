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
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jana.android.model.Image;
import com.jana.android.ui.R;
import com.jana.android.ui.impl.view.UrlImageView;
import com.jana.android.ui.viewmodel.IconPagerAdapter;
import com.jana.android.utils.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author islam
 */
public class ImagePagerAdapter<T extends Image> extends PagerAdapter implements
        IconPagerAdapter {

    private List<T> mImageList = new ArrayList<T>();

    private List<View> mViewsList = new ArrayList<View>();

    private int mIndicatorIconId;

    private int[] mIcons;

    private LayoutInflater mInflater;

    private ImageLoader mImageLoader = null;

    private DisplayImageOptions mOptions;

    public ImagePagerAdapter(Context context, final ImageLoader imageLoader,
                             List<T> imagesList) {

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mImageList = imagesList;

        mImageLoader = imageLoader;

        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        if (mIndicatorIconId > 0) {

            mIcons = new int[mImageList.size()];

            for (int i = 0; i < mIcons.length; i++) {

                mIcons[i] = mIndicatorIconId;
            }
        }

    }

    public void setIndicatorIcon(int resid) {
        mIndicatorIconId = resid;
    }

    public View getItem(int position) {

        if (mImageList == null || position < 0 || position >= mImageList.size()) {
            return null;
        }

        View view = mInflater.inflate(R.layout.simple_image_pagger_item, null,
                false);

        UrlImageView imageView = (UrlImageView) view.findViewById(R.id.image);

        final ProgressBar spinner = (ProgressBar) view
                .findViewById(R.id.loading);

        Image image = mImageList.get(position);

        if (image == null) {
            return view;
        }

        String imageUrl = image.getPath();

        if (!mImageLoader.isInited()) {
            return view;
        }

        mImageLoader.displayImage(imageUrl, imageView, mOptions,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                        spinner.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {

                        String message = null;

                        switch (failReason.getType()) {

                            case IO_ERROR:

                                message = "Input/Output error";

                                break;

                            case DECODING_ERROR:

                                message = "Image can't be decoded";

                                break;

                            case NETWORK_DENIED:

                                message = "Downloads are denied";

                                break;

                            case OUT_OF_MEMORY:

                                message = "Out Of Memory error";

                                break;

                            case UNKNOWN:

                                message = "Unknown error";

                                break;
                        }

                        Logger.w("Failed to load images due to this error = "
                                + message);

                        spinner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {

                        spinner.setVisibility(View.GONE);
                    }
                });

        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = getItem(position);

        while (mViewsList.size() <= position) {
            mViewsList.add(null);
        }

        mViewsList.set(position, view);

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);

        mImageList.set(position, null);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return mImageList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
     * java.lang.Object)
     */
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    @Override
    public int getIconResId(int index) {

        if (mIcons == null) {
            return android.R.drawable.btn_star;
        }

        return mIcons[index];
    }

}
