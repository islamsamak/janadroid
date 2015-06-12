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

package com.jana.android.ui.impl.view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.jana.android.model.MapModel;
import com.jana.android.ui.R;
import com.jana.android.ui.fragment.AbstractMapFragment;
import com.jana.android.utils.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.HashMap;

/**
 * @author islam
 */
public class MapInfoWindowAdapter implements InfoWindowAdapter {

    private LayoutInflater mInflater = null;

    private ImageLoader mImageLoader = null;

    private DisplayImageOptions mOptions;

    private AbstractMapFragment<? extends MapModel> mCallback;

    public MapInfoWindowAdapter(LayoutInflater inflater,
                                AbstractMapFragment<? extends MapModel> callback) {

        mInflater = inflater;

        mCallback = callback;

        mImageLoader = ImageLoader.getInstance();

        @SuppressWarnings("deprecation")
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(
                inflater.getContext()).memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(5 * 1024 * 1024).build();

        mImageLoader.init(configs);

        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return (null);
    }

    @Override
    public View getInfoContents(final Marker marker) {

        View popup = mInflater.inflate(R.layout.popup_map_info_window, null);

        TextView titleView = (TextView) popup.findViewById(R.id.txt_title);

        titleView.setText(marker.getTitle());

        String rawSnippet = marker.getSnippet();

        if (TextUtils.isEmpty(rawSnippet)) {

            Logger.e("Marker with title '" + marker.getTitle()
                    + "' has not snippet");

            return popup;
        }

        String[] items = rawSnippet.split(";SEP;");

        if (items.length >= 4) {

            UrlImageView thumbnail = (UrlImageView) popup
                    .findViewById(R.id.img_thumbnail);

            thumbnail.setImageResource(R.drawable.ic_launcher);

            if (mCallback != null) {

                HashMap<Marker, ImageView> map = mCallback.getMarkerViewMap();

                if (map != null && map.size() > 0) {

                    ImageView imageView = map.get(marker);

                    if (imageView != null) {

                        Drawable drawable = imageView.getDrawable();

                        if (drawable != null) {
                            thumbnail.setImageDrawable(drawable);
                        }
                    }
                }
            } else {

                String imageUrl = items[1];

                if (!TextUtils.isEmpty(imageUrl)) {
                    mImageLoader.displayImage(imageUrl, thumbnail, mOptions);
                }
            }

            String price = items[2];

            TextView priceView = (TextView) popup.findViewById(R.id.txt_price);

            priceView.setText(price);

            String snippet = items[3];

            TextView snippetView = (TextView) popup
                    .findViewById(R.id.txt_snippet);

            snippetView.setText(Html.fromHtml(snippet));

        } else {

            TextView snippetView = (TextView) popup
                    .findViewById(R.id.txt_snippet);

            snippetView.setText(Html.fromHtml(marker.getSnippet()));
        }

        return (popup);
    }
}
